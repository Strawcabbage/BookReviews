INSERT INTO book_genres (book_id, genre_id)
SELECT b.id, g.id
FROM (
         VALUES
             ('Refactoring', 'Nonfiction'),
             ('The Hobbit',  'Fantasy')
     ) AS m(book_name, genre_name)
         JOIN book  b ON b.name = m.book_name
         JOIN genre g ON g.name = m.genre_name
WHERE NOT EXISTS (
    SELECT 1 FROM book_genres bg
    WHERE bg.book_id = b.id AND bg.genre_id = g.id
);