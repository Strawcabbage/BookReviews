package com.example.bookreviews.mapper;

import com.example.bookreviews.dto.BookDTO;
import com.example.bookreviews.entity.Book;
import com.example.bookreviews.entity.Genre;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getRating(),
                book.getGenreList()
                        .stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList())
        );
    }

    // Optional: the other direction if you need it
    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.id());
        book.setName(dto.name());
        book.setAuthor(dto.author());
        book.setRating(dto.rating());
        // Genre list usually resolved separately
        return book;
    }
}
