package com.example.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String name;


}
