package com.bookreviews.controller;

import com.bookreviews.dto.CreateGenreRequest;
import com.bookreviews.entity.Genre;
import com.bookreviews.service.BookService;
import com.bookreviews.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public Genre getOne(Long id) {
        return genreService.getOne(id);
    }

    @PostMapping // consumes defaults to application/json
    public ResponseEntity<Genre> create(@Valid @RequestBody CreateGenreRequest req) {
        Genre created = genreService.create(req.name().trim());
        // 201 Created + Location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

}
