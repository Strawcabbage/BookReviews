package com.bookreviews.entity;

import java.util.List;

public interface BookSummary {
    Long getId();
    String getName();
    String getAuthor();
    String getPublishDate();
    Double getAverageRating();
    Long getReviewCount();
    List<String> getGenres();
}