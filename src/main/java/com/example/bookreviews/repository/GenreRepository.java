package com.example.bookreviews.repository;

import com.example.bookreviews.entity.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    public List<Genre> findByIdIn(List<Long> genre_ids);


}
