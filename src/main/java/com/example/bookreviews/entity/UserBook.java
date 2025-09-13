package com.example.bookreviews.entity;

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

    @Getter
    @Setter
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            optional = false,
            mappedBy = "BOOK"
    )
    private Book userBook;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="BOOK_ID", nullable=false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    public UserBook(User user, ReadStatus readStatus, int percentRead, Book book) {
        this.readStatus = readStatus;
        this.book = book;
        this.user = user;
        this.percentRead = percentRead;
    }

}
