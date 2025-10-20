package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "review",
        indexes = {
                @Index(name = "idx_review_book_id", columnList = "book_id")
        }
)
public class Review {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_book"))
    private Book book;

    @Getter
    @Setter
    @Column(name="rating")
    private Double rating;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    @Getter
    @Setter
    @Column(name="reccomendation")
    private Boolean recommendation;

    @Getter
    @Setter
    @Column(name="title")
    private String title;

    @Getter
    @Setter
    @Column(name="commentary")
    private String commentary;


    @Getter
    @Setter
    @Column(name="review_status")
    @Enumerated(EnumType.STRING) private ReviewStatus reviewStatus;

    private java.time.Instant createdAt;


}
