package com.bookreviews.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Getter
    @Setter
    @Column(name="AUTHOR")
    private String author;

    @Getter
    @Setter
    @Column(name="PUBLISH_DATE")
    private String publishDate;

    @ManyToMany
    @JoinTable(name="book_genres",
            joinColumns = { @JoinColumn(name="book_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private List<Genre> genreList;

    @Getter
    @Setter
    @Column(name="RATING")
    private double rating;

    @Getter
    @Setter
    @Column(name="READING_STATUS")
    private ReadStatus readStatus;

    @Getter
    @Setter
    @Column(name="PERCENT_READ")
    private double percentRead;

}
