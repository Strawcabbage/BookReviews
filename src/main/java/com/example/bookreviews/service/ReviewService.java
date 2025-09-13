package com.example.bookreviews.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import com.example.bookreviews.entity.Review;
import com.example.bookreviews.entity.ReviewStatus;
import com.example.bookreviews.entity.User;
import com.example.bookreviews.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review setStatus(User admin, Long reviewId, ReviewStatus newStatus) throws AccessDeniedException {

        var review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(
                "Review " + reviewId + " not found"));
        if (!admin.getAdmin() || review.getReviewStatus() != ReviewStatus.PENDING) throw new AccessDeniedException("Only an admin can change a pending review");

        review.setReviewStatus(newStatus);
        return reviewRepository.save(review);
    }

}
