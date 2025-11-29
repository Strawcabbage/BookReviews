package com.bookreviews.service;

import com.bookreviews.dto.*;
import com.bookreviews.entity.User;
import com.bookreviews.mapper.BookMapper;
import com.bookreviews.mapper.ReviewMapper;
import com.bookreviews.repository.BookRepository;
import com.bookreviews.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import com.bookreviews.entity.Review;
import com.bookreviews.entity.ReviewStatus;
import com.bookreviews.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookMapper bookMapper;
    private final ReviewMapper reviewMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public ReviewService(ReviewRepository reviewRepository, BookMapper bookMapper,
                         ReviewMapper reviewMapper, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookMapper = bookMapper;
        this.reviewMapper = reviewMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    Page<Review> findByBookId(Long bookId, Pageable pageable) {return reviewRepository.findByBookId(bookId, pageable);}

    public Page<Review> list(Pageable pageable) {return this.reviewRepository.findAll(pageable);}

    @Transactional
    public Page<BookListDTO> listBooks(Pageable pageable) {
        return reviewRepository.listBookAggregates(pageable)
                .map(bookMapper::toListDTO);
    }

    public ReviewDTO getOneDto(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review " + id + " not found"));

        return reviewMapper.toDto(review);

    }

    @Transactional
    public Review create(CreateReviewRequest c) {

        var book = bookRepository.findById(c.book_id())
                .orElseThrow(() -> new EntityNotFoundException("Book " + c.book_id() + " not found"));
        var user = userRepository.findById(c.user_id())
                .orElseThrow(() -> new EntityNotFoundException("User " + c.user_id() + " not found"));

        var title = Optional.ofNullable(c.title()).map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Title is required"));
        var commentary = Optional.ofNullable(c.commentary()).map(String::trim).orElse(null);

        Double rating = c.rating();
        if (rating == null || rating < 1.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setBook(book);
        review.setRating(c.rating());
        //var currentUser = userService.getCurrentUser(); // or from SecurityContext
        review.setUser(user);
        review.setTitle(c.title());
        review.setCommentary(c.commentary());
        review.setRecommendation(c.recommendation());
        review.setReviewStatus(ReviewStatus.PENDING);
        return reviewRepository.save(review);

    }

    @Transactional
    public ReviewDTO updatePartial(Long id, ReviewPatchDTO dto) {

        Review r = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Review " + id + " not found"));;

        String title = dto.title() == null ? null : dto.title().trim();
        String commentary = dto.commentary() == null ? null : dto.commentary().trim();

        if (title != null) {
            if (title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");
            r.setTitle(title);
        }
        if (commentary != null) {
            if (commentary.isBlank()) throw new IllegalArgumentException("Commentary cannot be blank");
            r.setCommentary(commentary);
        }
        if (dto.rating() != null) {
            Double rating = dto.rating();
            if (rating < 1.0 || rating > 5.0) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }
            r.setRating(dto.rating());
        }
        if (dto.recommendation() != null) {
            r.setRecommendation(dto.recommendation());
        }

        return reviewMapper.toDto(reviewRepository.save(r));

    }

    @Transactional
    public Review setStatus(User admin, Long reviewId, ReviewStatus newStatus) throws AccessDeniedException {

        var review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(
                "Review " + reviewId + " not found"));
        if (!admin.getAdmin() || review.getReviewStatus() != ReviewStatus.PENDING) throw new AccessDeniedException("Only an admin can change a pending review");

        review.setReviewStatus(newStatus);
        return reviewRepository.save(review);
    }

    @Transactional
    public void delete(User user, Long reviewId) throws AccessDeniedException {

        var review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(
                "Review " + reviewId + " not found"));

        if (!user.getAdmin() || !userRepository.existsById(review.getUser().getId())) {
            throw new AccessDeniedException("A review can only be deleted by an admin or the User who made the review");
        }

        reviewRepository.delete(review);

    }

}
