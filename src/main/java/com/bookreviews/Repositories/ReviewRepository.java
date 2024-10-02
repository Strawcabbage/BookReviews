package com.bookreviews.Repositories;

import com.bookreviews.Models.Review;
import com.bookreviews.Models.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    public List<Review> findByDisplayName(String displayName);

    public List<Review> findByRecommendation(boolean recommendation);

    public List<Review> findByReviewStatus(ReviewStatus reviewStatus);

    public List<Review> findByBookId(Long BookId);

}
