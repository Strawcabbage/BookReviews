package com.bookreviews.repository;

import com.bookreviews.entity.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    public Set<Genre> findByIdIn(List<Long> genre_ids);


}
