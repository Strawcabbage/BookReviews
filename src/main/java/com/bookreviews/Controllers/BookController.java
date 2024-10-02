package com.bookreviews.Controllers;

import com.bookreviews.Models.Book;
import com.bookreviews.Models.Genre;
import com.bookreviews.Services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/BookReviews")
public class BookController {

    private BookService bookService;

    @GetMapping("/books")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/books/author")
    public List<Book> getBookByAuthor(@RequestParam String author) {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookService.getBookByAuthor(author));

        return bookOptional.orElse(null);

    }

    @GetMapping("/books/name")
    public List<Book> getBookByName(@RequestParam String name) {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookService.getBookByName(name));

        return bookOptional.orElse(null);

    }

    @GetMapping("/books/publish_date")
    public List<Book> getBookByPublishDate(@RequestParam String publishDate) {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookService.getBookByPublishDate(publishDate));

        return bookOptional.orElse(null);
    }

    @GetMapping("/books/genre")
    public List<Book> getBookByGenre(@RequestParam Genre genre) {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookService.getBookByGenre(genre));

        return bookOptional.orElse(null);
    }

    @GetMapping("/books/rating")
    public List<Book> getBookByRatingGreaterThan(@RequestParam double rating) {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookService.getBookByRatingGreaterThan(rating));

        return bookOptional.orElse(null);
    }

    @PostMapping("/books/create")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/books/update/{id}")
    public Book createBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book existingBook = bookService.getBookById(id);

        if (existingBook != null) {
            if (updatedBook.getName() != null) {
                existingBook.setName(updatedBook.getName());
            }
            if (updatedBook.getAuthor() != null) {
                existingBook.setAuthor(updatedBook.getAuthor());
            }
            if (updatedBook.getPublishDate() != null) {
                existingBook.setPublishDate(updatedBook.getPublishDate());
            }
            if (updatedBook.getGenreList() != null) {
                existingBook.setGenreList(updatedBook.getGenreList());
            }
            if (updatedBook.getRating() != -1) {
                existingBook.setRating(updatedBook.getRating());
            }
            return bookService.updateBook(existingBook);
        } else {
            return null;
        }
    }

}
