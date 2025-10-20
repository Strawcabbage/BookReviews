
CREATE TABLE IF NOT EXISTS book_genres (
                                          book_id  BIGINT NOT NULL,
                                          genre_id BIGINT NOT NULL,
    CONSTRAINT pk_book_genres PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_bg_book  FOREIGN KEY (book_id)  REFERENCES book(id),
    CONSTRAINT fk_bg_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
    );

CREATE INDEX IF NOT EXISTS idx_book_genres_genre ON book_genres(genre_id, book_id);
