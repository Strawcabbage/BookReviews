package com.example.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="BOOK")
public class Book {

    @Id
    @Getter
    @Setter
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

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="BOOK_GENRES",
            joinColumns = { @JoinColumn(name="BOOK_ID") },
            inverseJoinColumns = { @JoinColumn(name="GENRE_ID") })
    private List<Genre> genreList = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "BOOK", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "BOOK")
    private List<UserBook> userBooks;


}
