
ALTER TABLE book
    ADD COLUMN publish_date TEXT;

UPDATE book SET publish_date = '2000-01-01' WHERE publish_date IS NULL;

ALTER TABLE book ALTER COLUMN publish_date SET NOT NULL;