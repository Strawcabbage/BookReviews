package com.bookreviews.mapper;

import com.bookreviews.dto.BookDTO;
import com.bookreviews.entity.BookSummary;
import com.bookreviews.entity.Genre;
import com.bookreviews.entity.Book;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDto(BookSummary s) {
        return new BookDTO(
                s.getId(),
                s.getName(),
                s.getAuthor(),
                s.getAverageRating(),
                s.getReviewCount(),
                java.util.List.of()
        );
    }

    public BookDTO toDto(Book book, Double averageRating, Long reviewCount) {
        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                averageRating,
                reviewCount,
                book.getGenreList()
                        .stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList())
        );
    }


    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.id());
        book.setName(dto.name());
        book.setAuthor(dto.author());
        return book;
    }
}
