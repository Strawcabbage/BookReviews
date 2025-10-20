package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="author")
    private String author;

    @Column(name="publish_date")
    private String publishDate;

    @ManyToMany
    @JoinTable(name="book_genres",
            joinColumns = { @JoinColumn(name="book_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<UserBook> userBooks;


}
