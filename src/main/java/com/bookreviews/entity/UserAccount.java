package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UserAccount {

    @Id
    @Getter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Version
    private Long version;

    @Getter
    @Setter
    private long balance;

}