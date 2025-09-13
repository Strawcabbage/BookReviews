package com.example.bookreviews.repository;

import com.example.bookreviews.entity.Review;
import com.example.bookreviews.entity.ReviewStatus;
import jakarta.annotation.Nullable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findByDisplayName(String displayName);

    public List<Review> findByRecommendation(boolean recommendation);

    public List<Review> findByReviewStatus(ReviewStatus reviewStatus);

    public List<Review> findByBookId(Long BookId);

    boolean existsByBookId(Long bookId);

}
