package com.example.bookreviews.repository;

import com.example.bookreviews.entity.Book;
import com.example.bookreviews.entity.BookSummary;
import com.example.bookreviews.entity.Review;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"genreList"})
    Page<Book> findAll(@Nullable Pageable pageable);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"genreList"})
    Optional<Book> findById(@Nullable Long id);


    @EntityGraph(attributePaths = {"genreList"})
    Page<Book> findByAuthorIgnoreCase(String author, Pageable pageable);

    @EntityGraph(attributePaths = {"genreList"})
    Page<Book> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    @EntityGraph(attributePaths = {"genreList"})
    Page<Book> findByGenreList_NameIgnoreCase(String genreName, Pageable pageable);

    @Query("""
           select distinct b
           from Book b
           left join b.genreList g
           where (:q is null
                     or lower(b.name)   like lower(concat('%', :q, '%'))
                     or lower(b.author) like lower(concat('%', :q, '%')))
             and (:genre is null or lower(g.name) = lower(:genre))
           """)
    @EntityGraph(attributePaths = {"genreList"})
    Page<Book> search(@Param("q") String q,
                      @Param("genre") String genre,
                      Pageable pageable);

    boolean existsByNameIgnoreCaseAndAuthorIgnoreCase(String name, String author);

    boolean existsByNameIgnoreCaseAndAuthorIgnoreCaseAndIdNot(String name, String author, Long id);

    @Query(value = """
        select b.id as id,
               b.name as name,
               b.author as author,
               coalesce(avg(r.rating), 0) as averageRating,
               count(r.id) as reviewCount
        from Book b
        left join Review r on r.book = b
        group by b.id, b.name, b.author
        """,
            countQuery = """
        select count(b) from Book b
        """)
    Page<BookSummary> listWithAggregates(Pageable pageable);

}
