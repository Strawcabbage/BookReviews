-- =========================================================
-- BOOKS (10 rows)
-- =========================================================
INSERT INTO book (name, author, publish_date) VALUES
                                                  ('Refactoring',              'Martin Fowler',        '1999-07-08'),
                                                  ('Clean Code',               'Robert C. Martin',     '2008-08-11'),
                                                  ('Effective Java',           'Joshua Bloch',         '2018-01-06'),
                                                  ('The Hobbit',               'J.R.R. Tolkien',       '1937-09-21'),
                                                  ('Dune',                     'Frank Herbert',        '1965-08-01'),
                                                  ('The Pragmatic Programmer', 'Andrew Hunt',          '1999-10-20'),
                                                  ('The Name of the Wind',     'Patrick Rothfuss',     '2007-03-27'),
                                                  ('Thinking, Fast and Slow',  'Daniel Kahneman',      '2011-10-25'),
                                                  ('The Martian',              'Andy Weir',            '2011-02-11'),
                                                  ('Sapiens',                  'Yuval Noah Harari',    '2011-06-04');

-- =========================================================
-- GENRES (10 rows)
-- =========================================================
INSERT INTO genre (name) VALUES
                             ('Fantasy'),
                             ('Sci-Fi'),
                             ('Nonfiction'),
                             ('Mystery'),
                             ('Romance'),
                             ('Horror'),
                             ('Biography'),
                             ('History'),
                             ('Self-Help'),
                             ('Programming');

-- =========================================================
-- USERS (10 rows)
-- =========================================================
INSERT INTO "users" (auth0Id, username, real_name, birth_date, email, admin) VALUES
                                                                                 ('auth0|alice',   'alice',   'Alice Smith',    '2001-01-15', 'alice@example.com',   TRUE),
                                                                                 ('auth0|bob',     'bob',     'Bob Johnson',    '2000-03-22', 'bob@example.com',     TRUE),
                                                                                 ('auth0|charlie', 'charlie', 'Charlie Brown',  '1999-07-09', 'charlie@example.com', FALSE),
                                                                                 ('auth0|diana',   'diana',   'Diana Prince',   '2002-11-02', 'diana@example.com',   FALSE),
                                                                                 ('auth0|ethan',   'ethan',   'Ethan Hunt',     '2001-05-30', 'ethan@example.com',   FALSE),
                                                                                 ('auth0|fiona',   'fiona',   'Fiona Gallagher','2003-02-10', 'fiona@example.com',   FALSE),
                                                                                 ('auth0|george',  'george',  'George Lucas',   '1998-09-19', 'george@example.com',  FALSE),
                                                                                 ('auth0|hannah',  'hannah',  'Hannah Baker',   '2000-12-05', 'hannah@example.com',  FALSE),
                                                                                 ('auth0|ian',     'ian',     'Ian Malcolm',    '1997-04-17', 'ian@example.com',     FALSE),
                                                                                 ('auth0|jenny',   'jenny',   'Jenny Lawson',   '1999-08-28', 'jenny@example.com',   FALSE);

-- =========================================================
-- BOOK_GENRES (>=10 rows, many-to-many via natural keys)
-- =========================================================
INSERT INTO book_genres (book_id, genre_id)
SELECT b.id, g.id
FROM (
         VALUES
             ('Refactoring',              'Programming'),
             ('Clean Code',               'Programming'),
             ('Effective Java',           'Programming'),
             ('The Hobbit',               'Fantasy'),
             ('Dune',                     'Sci-Fi'),
             ('The Pragmatic Programmer', 'Programming'),
             ('The Name of the Wind',     'Fantasy'),
             ('Thinking, Fast and Slow',  'Nonfiction'),
             ('The Martian',              'Sci-Fi'),
             ('Sapiens',                  'History'),
             ('Sapiens',                  'Nonfiction'),
             ('Thinking, Fast and Slow',  'Self-Help'),
             ('The Hobbit',               'Adventure'),
             ('Dune',                     'Adventure')
     ) AS m(book_name, genre_name)
         JOIN book  b ON b.name  = m.book_name
         JOIN genre g ON g.name  = m.genre_name
WHERE NOT EXISTS (
    SELECT 1
    FROM book_genres bg
    WHERE bg.book_id  = b.id
      AND bg.genre_id = g.id
);


-- =========================================================
-- USER_GENRES (>=10 rows, many-to-many via username + genre name)
-- =========================================================
INSERT INTO user_genres (user_id, genre_id)
SELECT u.id, g.id
FROM (
         VALUES
             ('alice',   'Programming'),
             ('alice',   'Fantasy'),
             ('bob',     'Programming'),
             ('bob',     'Nonfiction'),
             ('charlie', 'Fantasy'),
             ('diana',   'Sci-Fi'),
             ('ethan',   'Sci-Fi'),
             ('fiona',   'Romance'),
             ('george',  'History'),
             ('hannah',  'Self-Help'),
             ('ian',     'Nonfiction'),
             ('jenny',   'Programming')
     ) AS m(username, genre_name)
         JOIN "users" u ON u.username = m.username
         JOIN genre   g ON g.name     = m.genre_name
WHERE NOT EXISTS (
    SELECT 1
    FROM user_genres ug
    WHERE ug.user_id  = u.id
      AND ug.genre_id = g.id
);

-- =========================================================
-- USER_BOOK (10 rows, one per user for testing)
-- =========================================================
INSERT INTO user_book (read_status, percent_read, book_id, user_id)
SELECT m.read_status, m.percent_read, b.id, u.id
FROM (
         VALUES
             ('alice',   'Refactoring',              'READ', 100),
             ('bob',     'Clean Code',               'READING',    60),
             ('charlie', 'The Hobbit',               'UNREAD', 0),
             ('diana',   'Dune',                     'READING',    40),
             ('ethan',   'The Pragmatic Programmer', 'READING',    30),
             ('fiona',   'The Name of the Wind',     'READ', 100),
             ('george',  'Thinking, Fast and Slow',  'UNREAD', 0),
             ('hannah',  'The Martian',              'READING',    50),
             ('ian',     'Sapiens',                  'READ', 100),
             ('jenny',   'Effective Java',           'READING',    20)
     ) AS m(username, book_name, read_status, percent_read)
         JOIN "users" u ON u.username = m.username
         JOIN book    b ON b.name     = m.book_name;

-- =========================================================
-- REVIEWS (10 rows, joined by username + book name)
-- =========================================================
INSERT INTO review (
    rating,
    username,
    title,
    recommendation,
    review_status,
    commentary,
    created_at,
    book_id,
    user_id
)
SELECT
    m.rating,
    u.username,
    m.title,
    m.recommendation,
    m.review_status,
    m.commentary,
    CURRENT_TIMESTAMP,
    b.id,
    u.id
FROM (
         VALUES
             ('alice',   'Refactoring',              5.0, 'Loved it',        TRUE,  'APPROVED', 'Essential reading for any dev.'),
             ('bob',     'Clean Code',               4.5, 'Very helpful',    TRUE,  'APPROVED', 'Improved the way I think about code.'),
             ('charlie', 'The Hobbit',               4.0, 'Classic',         TRUE,  'APPROVED', 'Charming adventure story.'),
             ('diana',   'Dune',                     4.5, 'Epic sci-fi',     TRUE,  'APPROVED', 'Slow start but incredible worldbuilding.'),
             ('ethan',   'The Pragmatic Programmer', 4.0, 'Great tips',      TRUE,  'APPROVED', 'Full of practical advice.'),
             ('fiona',   'The Name of the Wind',     5.0, 'Beautiful',       TRUE,  'APPROVED', 'Gorgeous prose and characters.'),
             ('george',  'Thinking, Fast and Slow',  4.0, 'Thought-provoking', TRUE, 'APPROVED', 'Changed how I view decision making.'),
             ('hannah',  'The Martian',              4.5, 'Fun read',        TRUE,  'APPROVED', 'Science + humor = great combo.'),
             ('ian',     'Sapiens',                  4.0, 'Interesting',     TRUE,  'APPROVED', 'Broad overview of human history.'),
             ('jenny',   'Effective Java',           4.5, 'Deep dive',       TRUE,  'APPROVED', 'Fantastic for Java best practices.')
     ) AS m(username, book_name, rating, title, recommendation, review_status, commentary)
         JOIN "users" u ON u.username = m.username
         JOIN book    b ON b.name     = m.book_name;
