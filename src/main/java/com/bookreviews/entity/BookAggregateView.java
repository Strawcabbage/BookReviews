package com.bookreviews.entity;


public interface BookAggregateView {

    BookBasic getBook();
    Double getAvgRating();
    Long getReviewCount();

    public interface BookBasic {

        Long getId();
        String getName();
        String getAuthor();
        String getPublishDate();

    }

}
