package com.example.bookreviews.entity;

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
    @JoinTable(name="USER_GENRES",
            joinColumns = { @JoinColumn(name="USER_ID") },
            inverseJoinColumns = { @JoinColumn(name="GENRE_ID") })
    private List<Genre> genreList = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "USER", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBook> userBooks = new ArrayList<>();

    @Setter
    @Column(name="ADMIN")
    private boolean admin;


    public boolean getAdmin() {
        return this.admin;
    }

}
