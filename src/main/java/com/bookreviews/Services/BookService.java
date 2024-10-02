package com.bookreviews.Services;

import com.bookreviews.Models.Book;
import com.bookreviews.Models.Genre;
import com.bookreviews.Repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return this.bookRepository.findById(id).orElse(null);
    }

    public List<Book> getBookByAuthor(String author) {
        return this.bookRepository.findByAuthor(author);
    }

    public List<Book> getBookByName(String name) {
        return this.bookRepository.findByName(name);
    }

    public List<Book> getBookByPublishDate(String publishDate) {
        return this.bookRepository.findByAuthor(publishDate);
    }

    public List<Book> getBookByGenre(Genre genre) {
        return this.bookRepository.findByGenreListIsContainingIgnoreCase(genre);
    }

    public List<Book> getBookByRatingGreaterThan(double rating) {
        return this.bookRepository.findByRatingGreaterThan(rating);
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return this.bookRepository.save(book);
    }

}
