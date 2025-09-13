package com.example.bookreviews.entity;

import java.util.List;

public interface BookSummary {
    Long getId();
    String getName();
    String getAuthor();
    Double getAverageRating();
    Long getReviewCount();
    List<String> getGenres();
}