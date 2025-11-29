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
    public ResponseEntity<BookDTO> create(@Valid @RequestBody CreateBookRequest req) {
        Book created = bookService.create(req);
        return ResponseEntity.ok(bookService.getOneDto(created.getId()));
    }

    @PatchMapping("/{id}")
    public BookDTO patch(@PathVariable Long id, @RequestBody BookPatchDTO dto) {
        Book updated = bookService.updatePartial(id, dto);
        return bookService.getOneDto(updated.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
