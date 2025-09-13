package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name="name")
    private String name;

    @Getter
    @Setter
    @Column(name="author")
    private String author;

    @Getter
    @Setter
    @Column(name="publishDate")
    private String publishDate;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="book_genres",
            joinColumns = { @JoinColumn(name="book_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private Set<Genre> genreList = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<UserBook> userBooks;


}
