package com.bookreviews.mapper;

import com.bookreviews.dto.BookDTO;
import com.bookreviews.dto.BookListDTO;
import com.bookreviews.entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class BookMapper {

    public BookDTO toDto(BookSummary s) {
        return new BookDTO(
                s.getId(),
                s.getName(),
                s.getAuthor(),
                s.getPublishDate(),
                s.getAverageRating(),
                s.getReviewCount(),
                List.of()
        );
    }

    public BookDTO toDto(Book book, Double averageRating, Long reviewCount) {
        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getPublishDate(),
                averageRating,
                reviewCount,
                book.getGenres()
                        .stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList())
        );
    }


    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setName(dto.name());
        book.setAuthor(dto.author());
        return book;
    }

    public BookListDTO toListDTO(BookAggregateView v) {

        var b = v.getBook();
        return new BookListDTO(
                b.getId(), b.getName(), b.getAuthor(), b.getPublishDate(),
                v.getAvgRating() != null ? v.getAvgRating() : 0.0,
                v.getReviewCount() != null ? v.getReviewCount() : 0L
        );

    }

}
