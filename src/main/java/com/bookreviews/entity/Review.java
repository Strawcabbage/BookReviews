package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "REVIEW",
        indexes = {
                @Index(name = "idx_review_book_id", columnList = "book_id")
        }
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name="displayName")
    private String displayName;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_book"))
    private Book book;

    @Getter
    @Setter
    @Column(name="rating")
    private double rating;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    @Getter
    @Setter
    @Column(name="reccomendation")
    private boolean recommendation;

    @Getter
    @Setter
    @Column(name="commentary")
    private String commentary;

    @Getter
    @Setter
    @Column(name="reviewStatus")
    private ReviewStatus reviewStatus;


}
