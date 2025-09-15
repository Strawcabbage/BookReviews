CREATE TABLE genre (
                       id   BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL UNIQUE
);

CREATE TABLE book (
                      id          BIGSERIAL PRIMARY KEY,
                      title       TEXT NOT NULL,
                      author      TEXT,
                      genre_id    BIGINT REFERENCES genre(id) ON DELETE SET NULL
);