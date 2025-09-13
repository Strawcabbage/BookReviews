package com.bookreviews.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import com.bookreviews.entity.Review;
import com.bookreviews.entity.ReviewStatus;
import com.bookreviews.entity.User;
import com.bookreviews.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    Page<Review> findByBookId(Long bookId, Pageable pageable) {return reviewRepository.findByBookId(bookId, pageable);}

    @Transactional
    public Review setStatus(User admin, Long reviewId, ReviewStatus newStatus) throws AccessDeniedException {

        var review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(
                "Review " + reviewId + " not found"));
        if (!admin.getAdmin() || review.getReviewStatus() != ReviewStatus.PENDING) throw new AccessDeniedException("Only an admin can change a pending review");

        review.setReviewStatus(newStatus);
        return reviewRepository.save(review);
    }

}
