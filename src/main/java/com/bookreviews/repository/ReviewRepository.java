package com.bookreviews.repository;

import com.bookreviews.entity.*;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {


    public List<Review> findByRecommendation(boolean recommendation);

    public List<Review> findByReviewStatus(ReviewStatus reviewStatus);

    public List<Review> findByBookId(Long BookId);

    Page<Review> findByBookId(Long bookId, Pageable pageable);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"genres"})
    Page<Review> findAll(@Nullable Pageable pageable);

    @Query(value = """
                select b as book,
                coalesce(avg(r.rating),0) as avgRating,
                count(r.id) as reviewCount
            from Review r
            join r.book b
            group by b
            """,
            countQuery = "select count(distinct b.id) from Review r join r.book b")
    Page<BookAggregateView> listBookAggregates(Pageable pageable);




    boolean existsByBookId(Long bookId);

    @Query("""
        select coalesce(avg(r.rating), 0.0)
        from Review r
        where r.book.id = :bookId
    """)
    Double avgRatingByBookId(@Param("bookId") Long id);

    @Query("""
        select count(r)
        from Review r
        where r.book.id = :bookId
    """)
    long countByBookId(@Param("bookId") Long id);
}
