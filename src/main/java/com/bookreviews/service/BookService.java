package com.bookreviews.service;

import com.bookreviews.dto.BookDTO;
import com.bookreviews.dto.BookPatchDTO;
import com.bookreviews.dto.CreateBookRequest;
import com.bookreviews.entity.Book;
import com.bookreviews.entity.BookSummary;
import com.bookreviews.mapper.BookMapper;
import com.bookreviews.entity.Genre;
import com.bookreviews.repository.BookRepository;
import com.bookreviews.repository.GenreRepository;
import com.bookreviews.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;

    private final BookMapper bookMapper;


    public BookService(BookRepository bookRepository, GenreRepository genreRepository, ReviewRepository reviewRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
        this.bookMapper = mapper;
    }


    public Page<Book> list(Pageable pageable) {return this.bookRepository.findAll(pageable);}

    public Book getById(Long id) {return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));}

    @Transactional
    public Page<BookDTO> listWithAggregatesAsDto(Pageable pageable) {
        return bookRepository.listWithAggregates(pageable)
                .map(bookMapper::toDto);
    }

    @Transactional
    public BookDTO getDetail(Long id) {
        Book b = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));
        Double avg = reviewRepository.avgRatingByBookId(id);
        long count = reviewRepository.countByBookId(id);
        return bookMapper.toDto(b, avg, count);
    }

    @Transactional
    public Page<BookDTO> listDto(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(b -> bookMapper.toDto(b,
                        reviewRepository.avgRatingByBookId(b.getId()),
                        reviewRepository.countByBookId(b.getId())));
    }

    @Transactional
    public BookDTO getOneDto(Long id) {
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found"));
        Double avg = reviewRepository.avgRatingByBookId(id);
        long count = reviewRepository.countByBookId(id);
        return bookMapper.toDto(b, avg, count);
    }

    @Transactional
    public Book create(CreateBookRequest req) {

        if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCase(req.name(), req.author())) {
            throw new IllegalArgumentException("A book with this name and author already exists.");
        }

        Set<Genre> genres = genreRepository.findByIdIn((req.genreIds()));
        if (genres.size() != req.genreIds().size()) {
            throw new EntityNotFoundException("One or more genreIds do not exist.");
        }


        Book book = new Book();
        book.setName(req.name().trim());
        book.setAuthor(req.author().trim());
        book.setPublishDate(req.publish_date().trim());
        book.setGenres(genres);

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
            if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(newName, existing.getAuthor(), id)) {
                throw new IllegalArgumentException("A book with this name and author already exists.");
            } else {
                existing.setName(newName);
            }
        } else if (dto.author() != null) {
            if (bookRepository.existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(existing.getName(), newAuthor, id)) {
                throw new IllegalArgumentException("A book with this name and author already exists.");
            } else {
                existing.setAuthor(newAuthor);
            }
        }

        if (dto.publish_date() != null) {
            existing.setPublishDate(dto.publish_date());
        }

        if (dto.genreIds() != null) {
            Set<Genre> genres = genreRepository.findByIdIn(dto.genreIds());
            if (genres.size() != dto.genreIds().size()) {
                throw new EntityNotFoundException("One or more genreIds do not exist.");
            } else {
                existing.setGenres(genres);
            }
        }
        return bookRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book " + id + " not found."));

        if (reviewRepository.existsByBookId(id)) {
            throw new IllegalStateException("Cannot delete a book with reviews.");
        }

        book.getGenres().clear();

        bookRepository.delete(book);
    }

    public Page<BookDTO> search(String q, String genre, Pageable pageable) {
        return bookRepository.search(q, genre, pageable).map(bookMapper::toDto);
    }

}
