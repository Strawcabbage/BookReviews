CREATE TABLE IF NOT EXISTS "user" (
                                      id           BIGSERIAL PRIMARY KEY,
                                      display_name     TEXT        NOT NULL UNIQUE,
                                      real_name    TEXT      NOT NULL,
                                      birth_date   TEXT        NOT NULL,
                                      email        TEXT        NOT NULL UNIQUE,
                                      password     TEXT        NOT NULL,
                                      password_hash     TEXT        NOT NULL,

                                      admin        BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS user_genres (
                                           user_id  BIGINT NOT NULL,
                                           genre_id BIGINT NOT NULL,
                                           CONSTRAINT pk_user_genres PRIMARY KEY (user_id, genre_id),
    CONSTRAINT fk_ug_user  FOREIGN KEY (user_id)  REFERENCES "user"(id),
    CONSTRAINT fk_ug_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
    );

CREATE INDEX IF NOT EXISTS idx_user_display_name ON "user"(display_name);
CREATE INDEX IF NOT EXISTS idx_user_email    ON "user"(email);
CREATE INDEX IF NOT EXISTS idx_user_genres_user  ON user_genres(user_id);
CREATE INDEX IF NOT EXISTS idx_user_genres_genre ON user_genres(genre_id);

CREATE TABLE IF NOT EXISTS user_book (
                                         id            BIGSERIAL PRIMARY KEY,

                                         read_status   VARCHAR(32),
    percent_read  INT         NOT NULL DEFAULT 0,

    book_id       BIGINT      NOT NULL,
    user_id       BIGINT      NOT NULL,

    CONSTRAINT fk_userbook_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_userbook_user FOREIGN KEY (user_id) REFERENCES "user"(id)
    );

CREATE INDEX IF NOT EXISTS idx_userbook_user ON user_book(user_id, book_id);
CREATE INDEX IF NOT EXISTS idx_userbook_book ON user_book(book_id, user_id);


CREATE TABLE IF NOT EXISTS review (
                                      id           BIGSERIAL PRIMARY KEY,
                                      rating       FLOAT    NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                      display_name TEXT NOT NULL,
    title TEXT,
    reccomendation     BOOLEAN,
    review_status     VARCHAR(32),
    commentary      TEXT,
    created_at   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,

    book_id      BIGINT      NOT NULL,
    user_id      BIGINT      NOT NULL,

    CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES "user"(id)
    );

CREATE INDEX IF NOT EXISTS idx_review_book ON review(book_id);
CREATE INDEX IF NOT EXISTS idx_review_user ON review(user_id);
