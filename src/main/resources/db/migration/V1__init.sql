CREATE TABLE genre (
                       id   BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL UNIQUE
);

CREATE TABLE book (
                      name        TEXT NOT NULL,
                      author      TEXT NOT NULL,
                      id          BIGSERIAL PRIMARY KEY

);