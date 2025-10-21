package com.bookreviews.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Getter
    @Setter
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;


    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(name="username")
    private String username;

    @Getter
    @Setter
    @Column(name="real_name")
    private String realName;


    @Getter
    @Setter
    @Column(name="birth_date")
    private String birthDate;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="user_genres",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private Set<Genre> userGenres = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBook> userBooks = new ArrayList<>();

    @Getter
    @Setter
    @Column(name="admin")
    private Boolean admin;


}
