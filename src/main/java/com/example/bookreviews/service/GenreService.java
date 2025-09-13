package com.example.bookreviews.service;

import com.example.bookreviews.entity.Genre;
import com.example.bookreviews.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {this.genreRepository = genreRepository;}

    public List<Genre> resolveByIds(List<Long> genre_ids) {return this.genreRepository.findByIdIn(genre_ids);}

}
