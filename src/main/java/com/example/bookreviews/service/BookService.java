package com.example.bookreviews.service;

import com.example.bookreviews.dto.BookDTO;
import com.example.bookreviews.dto.BookPatchDTO;
import com.example.bookreviews.dto.CreateBookRequest;
import com.example.bookreviews.entity.Book;
import com.example.bookreviews.entity.BookSummary;
import com.example.bookreviews.entity.Genre;
import com.example.bookreviews.repository.BookRepository;
import com.example.bookreviews.repository.GenreRepository;
import com.example.bookreviews.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;


    public BookService(BookRepository bookRepository, GenreRepository genreRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }


    public Page<Book> list(Pageable pageable) {return this.bookRepository.findAll(pageable);}

    public Book getById(Long id) {return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));}

    public Page<BookDTO> listWithAggregatesAsDto(Pageable pageable) {
        return bookRepository.listWithAggregates(pageable)
                .map(s -> new BookDTO(
                        s.getId(),
                        s.getName(),
                        s.getAuthor(),
                        s.getAverageRating(),
                        s.getReviewCount(),
                        s.getGenres()
                ));
    }

    @Transactional
    public Book create(CreateBookRequest req) {

        if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCase(req.name(), req.author())) {
            throw new IllegalArgumentException("A book with this name and author already exists.");
        }

        List<Genre> genres = genreRepository.findByIdIn((req.genreIds()));
        if (genres.size() != req.genreIds().size()) {
            throw new EntityNotFoundException("One or more genreIds do not exist.");
        }


        Book book = new Book();
        book.setName(req.name().trim());
        book.setAuthor(req.author().trim());
        book.setGenreList(genres);

        return bookRepository.save(book);
    }

    @Transactional
    public Book updatePartial(Long id, BookPatchDTO dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));

        String newName   = dto.name()   != null ? dto.name().trim()   : null;
        String newAuthor = dto.author() != null ? dto.author().trim() : null;

        List<Long> newGenreIds = dto.genreIds();


        if (newName != null && newAuthor != null) {
            if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(newName, newAuthor, id)) {
                throw new IllegalArgumentException("A book with this name and author already exists.");
            } else {
                existing.setName(newName);
                existing.setAuthor(newAuthor);
            }

        } else if (dto.name() != null) {
            if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(newName, newAuthor, id)) {
                throw new IllegalArgumentException("A book with this name and author already exists.");
            } else {
                existing.setName(newName);
            }
        } else if (dto.author() != null) {
            if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(newName, newAuthor, id)) {
                throw new IllegalArgumentException("A book with this name and author already exists.");
            } else {
                existing.setAuthor(newAuthor);
            }
        }

        if (dto.genreIds() != null) {
            List<Genre> genres = genreRepository.findByIdIn(dto.genreIds());
            if (genres.size() != dto.genreIds().size()) {
                throw new EntityNotFoundException("One or more genreIds do not exist.");
            } else {
                existing.setGenreList(genres);
            }
        }
        return bookRepository.save(existing);
    }

    // Optional update is only allowing admins to delete books
    @Transactional
    public void delete(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found."));

        if (reviewRepository.existsByBookId(id)) {
            throw new IllegalStateException("Cannot delete a book with reviews.");
        }

        book.getGenreList().clear();

        bookRepository.delete(book);
    }

}
