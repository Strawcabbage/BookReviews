package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(force = true)
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING) private ReadStatus readStatus;

    @Getter
    @Setter
    private int percentRead;


    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id", nullable=false)
    private User user;

    public UserBook(User user, ReadStatus readStatus, int percentRead, Book book) {
        this.readStatus = readStatus;
        this.book = book;
        this.user = user;
        this.percentRead = percentRead;
    }

}
