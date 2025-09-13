package com.example.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "REVIEW",
        indexes = {
                @Index(name = "idx_review_book_id", columnList = "BOOK_ID")
        }
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Getter
    @Setter
    @Column(name="BOOK_ID")
    private Long bookId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="BOOK_ID", nullable=false)
    @Column(name="Book")
    private Book book;

    @Getter
    @Setter
    @Column(name="RATING")
    private double rating;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="USER_ID", nullable=false)
    @Column(name="USER")
    private User user;

    @Getter
    @Setter
    @Column(name="RECCOMENDATION")
    private boolean recommendation;

    @Getter
    @Setter
    @Column(name="COMMENTARY")
    private String commentary;

    @Getter
    @Setter
    @Column(name="REVIEW_STATUS")
    private ReviewStatus reviewStatus;


}
