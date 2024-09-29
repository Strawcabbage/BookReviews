package com.bookreviews.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Getter
    @Setter
    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Getter
    @Setter
    @Column(name="RATING")
    private double rating;

    @Getter
    @Setter
    @Column(name="Recommendation")
    private boolean recommendation;

    @Getter
    @Setter
    @Column(name="COMMENTARY")
    private String commentary;

    @Getter
    @Column(name="REVIEW_STATUS")
    private ReviewStatus reviewStatus;

    public void setReviewStatus(User user, Review review, ReviewStatus newReviewStatus) {
        if (user.getAdmin() && review.reviewStatus == ReviewStatus.Pending) {
            review.reviewStatus = newReviewStatus;
        }
    }

}
