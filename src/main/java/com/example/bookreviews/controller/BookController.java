package com.example.bookreviews.controller;

import com.example.bookreviews.dto.BookDTO;
import com.example.bookreviews.dto.BookPatchDTO;
import com.example.bookreviews.dto.CreateBookRequest;
import com.example.bookreviews.entity.Book;
import com.example.bookreviews.mapper.BookMapper;
import com.example.bookreviews.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper mapper;

    @GetMapping
    public Page<BookDTO> list(
            Pageable pageable,
            @RequestParam(defaultValue = "summary") String view
    ) {
        if ("summary".equalsIgnoreCase(view)) {
            return bookService.listWithAggregatesAsDto(pageable);
        }
        return bookService.list(pageable).map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable Long id) {
        Book book = bookService.getById(id);
        return mapper.toDto(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody CreateBookRequest req) {
        Book created = bookService.create(req);
        return ResponseEntity.ok(mapper.toDto(created));
    }

    @PatchMapping("/{id}")
    public BookDTO patch(@PathVariable Long id, @RequestBody BookPatchDTO dto) {
        Book updated = bookService.updatePartial(id, dto);
        return mapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
