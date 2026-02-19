package com.bookreviews.service;

import com.bookreviews.entity.Genre;
import com.bookreviews.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {this.genreRepository = genreRepository;}

    public Set<Genre> resolveByIds(List<Long> genre_ids) {return this.genreRepository.findByIdIn(genre_ids);}

    public Genre getOne(Long id) {return this.genreRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Genre " + id + " not found"));}

    public List<Genre> getAll() {
        return (List<Genre>) this.genreRepository.findAll();
    }

    @Transactional
    public Genre create(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
        return genre;
    }

    public void delete(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre " + id + " not found."));
        genreRepository.delete(genre);
    }

}
