package com.bookreviews.controller;

import com.bookreviews.dto.BookDTO;
import com.bookreviews.dto.BookPatchDTO;
import com.bookreviews.dto.CreateBookRequest;
import com.bookreviews.entity.Book;
import com.bookreviews.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<BookDTO> list(
            Pageable pageable,
            @RequestParam(defaultValue = "summary") String view
    ) {
        if ("summary".equalsIgnoreCase(view)) {
            return bookService.listWithAggregatesAsDto(pageable);
        }
        return bookService.listDto(pageable);
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable Long id) {
        return bookService.getOneDto(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> create(@Valid @RequestBody CreateBookRequest req) {
        return ResponseEntity.ok(bookService.create(req));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDTO patch(@PathVariable Long id, @RequestBody BookPatchDTO dto) {
        return bookService.updatePartial(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Page<BookDTO> search(@RequestParam(required = false) String q,
                             @RequestParam(required = false) String genre,
                             Pageable pageable) {
        return bookService.search(q, genre, pageable);
    }

}
