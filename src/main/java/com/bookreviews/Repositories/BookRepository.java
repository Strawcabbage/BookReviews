package com.bookreviews.Repositories;

import com.bookreviews.Models.Book;
import com.bookreviews.Models.Genre;
import com.bookreviews.Models.ReadStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    public List<Book> findByAuthor(String author);

    public List<Book> findByName(String name);

    public List<Book> findByPublishDate(String publishDate);

    public List<Book> findByGenreListIsContainingIgnoreCase(Genre genre);

    public List<Book> findByRatingGreaterThan(double rating);

}
