package com.bookreviews.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Getter
    @Setter
    @Column(name="REAL_NAME")
    private String realName;


    @Getter
    @Setter
    @Column(name="BIRTH_DATE")
    private String birthDate;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="user_genres",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private List<Genre> genreList;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="read_user_books",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="book_id") })
    private List<UserBook> readBookList;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="wishlist_user_books",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="book_id") })
    private List<UserBook> wishlistBookList;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="reading_user_books",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="book_id") })
    private List<UserBook> readingBookList;

    @Setter
    @Column(name="ADMIN")
    private boolean admin;


    public boolean getAdmin() {
        return this.admin;
    }
}
