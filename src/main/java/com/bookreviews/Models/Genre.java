package com.bookreviews.Models;

import jakarta.persistence.*;

@Entity
@Table(name="GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
