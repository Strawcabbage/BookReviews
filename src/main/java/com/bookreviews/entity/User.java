package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name="displayName")
    private String displayName;

    @Getter
    @Setter
    @Column(name="realName")
    private String realName;


    @Getter
    @Setter
    @Column(name="birthDate")
    private String birthDate;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="user_genres",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name="genre_id") })
    private List<Genre> genreList = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBook> userBooks = new ArrayList<>();

    @Setter
    @Column(name="admin")
    private boolean admin;


    public boolean getAdmin() {
        return this.admin;
    }

}
