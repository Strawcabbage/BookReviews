package com.bookreviews.Models;

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
    private ReadStatus readStatus;

    @Getter
    @Setter
    private int percentRead;

    @Getter
    @Setter
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            optional = false,
            mappedBy = "book"
    )
    private Book userBook;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            optional = false,
            mappedBy = "user"
    )
    private User userProfile;

    public UserBook(User user, ReadStatus readStatus, int percentRead, Book book) {
        this.readStatus = readStatus;
        this.userBook = book;
        this.userProfile = user;
        this.percentRead = percentRead;
        this.id = book.getId();
    }

}
