-- Insert the books (no genre_id column anymore)
INSERT INTO book(name, author) VALUES
                                    ('Refactoring', 'Martin Fowler'),
                                    ('The Hobbit',  'J.R.R. Tolkien');

-- Insert the genres
INSERT INTO genre(name) VALUES ('Fantasy'), ('Sci-Fi'), ('Nonfiction')
-- ON CONFLICT DO NOTHING;  -- keep if supported
