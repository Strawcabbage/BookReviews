-- =========================
-- GENRE
-- =========================
CREATE TABLE genre (
                       id   BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL UNIQUE
);

-- =========================
-- USERS
-- =========================
CREATE TABLE "users" (
                         id         BIGSERIAL PRIMARY KEY,
                         auth0Id    TEXT    NOT NULL,
                         username   TEXT    NOT NULL UNIQUE,
                         real_name  TEXT    NOT NULL,
                         birth_date TEXT,
                         email      TEXT    NOT NULL UNIQUE,
                         admin      BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_user_username ON "users"(username);
CREATE INDEX idx_user_email    ON "users"(email);

-- =========================
-- BOOK
-- =========================
CREATE TABLE book (
                      id           BIGSERIAL PRIMARY KEY,
                      name         TEXT NOT NULL,
                      author       TEXT NOT NULL,
                      publish_date TEXT NOT NULL
);

-- =========================
-- BOOK_GENRES (many-to-many Book <-> Genre)
-- =========================
CREATE TABLE book_genres (
                             book_id  BIGINT NOT NULL,
                             genre_id BIGINT NOT NULL,
                             CONSTRAINT pk_book_genres PRIMARY KEY (book_id, genre_id),
                             CONSTRAINT fk_bg_book  FOREIGN KEY (book_id)  REFERENCES book(id),
                             CONSTRAINT fk_bg_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE INDEX idx_book_genres_genre ON book_genres(genre_id, book_id);

-- =========================
-- USER_GENRES (many-to-many User <-> Genre)
-- =========================
CREATE TABLE user_genres (
                             user_id  BIGINT NOT NULL,
                             genre_id BIGINT NOT NULL,
                             CONSTRAINT pk_user_genres PRIMARY KEY (user_id, genre_id),
                             CONSTRAINT fk_ug_user  FOREIGN KEY (user_id)  REFERENCES "users"(id),
                             CONSTRAINT fk_ug_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE INDEX idx_user_genres_user  ON user_genres(user_id);
CREATE INDEX idx_user_genres_genre ON user_genres(genre_id);

-- =========================
-- USER_BOOK (reading status per user & book)
-- =========================
CREATE TABLE user_book (
                           id           BIGSERIAL PRIMARY KEY,
                           read_status  VARCHAR(32),
                           percent_read INT         NOT NULL DEFAULT 0,

                           book_id      BIGINT      NOT NULL,
                           user_id      BIGINT      NOT NULL,

                           CONSTRAINT fk_userbook_book FOREIGN KEY (book_id) REFERENCES book(id),
                           CONSTRAINT fk_userbook_user FOREIGN KEY (user_id) REFERENCES "users"(id)
);

CREATE INDEX idx_userbook_user ON user_book(user_id, book_id);
CREATE INDEX idx_userbook_book ON user_book(book_id, user_id);

-- =========================
-- REVIEW
-- =========================
CREATE TABLE review (
                        id              BIGSERIAL PRIMARY KEY,
                        rating          FLOAT    NOT NULL CHECK (rating BETWEEN 1 AND 5),
                        username        TEXT     NOT NULL,
                        title           TEXT,
                        recommendation  BOOLEAN,
                        review_status   VARCHAR(32),
                        commentary      TEXT,
                        created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        book_id         BIGINT   NOT NULL,
                        user_id         BIGINT   NOT NULL,

                        CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book(id),
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES "users"(id)
);

CREATE INDEX idx_review_book ON review(book_id);
CREATE INDEX idx_review_user ON review(user_id);