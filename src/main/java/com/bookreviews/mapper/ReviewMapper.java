package com.bookreviews.mapper;

import com.bookreviews.dto.ReviewDTO;
import com.bookreviews.entity.Review;
import com.bookreviews.entity.ReviewSummaryView;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(ReviewSummaryView v) {
        return new ReviewDTO(
                v.getId(),
                v.getBook().getId(),
                v.getBook().getName(),
                v.getRating(),
                v.getUser().getId(),
                v.getUser().getUsername(),
                v.getTitle(),
                v.getRecommendation(),
                v.getCommentary(),
                v.getReviewStatus()
        );
    }

    public ReviewDTO toDto(Review r) {
        return new ReviewDTO(
                r.getId(),
                r.getBook().getId(),
                r.getBook().getName(),
                r.getRating(),
                r.getUser().getId(),
                r.getUser().getUsername(),
                r.getTitle(),
                r.getRecommendation(),
                r.getTitle(),
                r.getReviewStatus()
        );
    }

    public Review toEntity(ReviewDTO dto) {

        Review review = new Review();
        review.setRating(dto.rating());
        review.setCommentary(dto.commentary());
        review.setRecommendation(dto.recommendation());

        return review;

    }


}
