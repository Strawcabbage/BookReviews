package com.bookreviews.repository;

import com.bookreviews.entity.Review;
import com.bookreviews.entity.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findByDisplayName(String displayName);

    public List<Review> findByRecommendation(boolean recommendation);

    public List<Review> findByReviewStatus(ReviewStatus reviewStatus);

    public List<Review> findByBookId(Long BookId);

    Page<Review> findByBookId(Long bookId, Pageable pageable);

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
