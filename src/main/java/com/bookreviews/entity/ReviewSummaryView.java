package com.bookreviews.entity;

public interface ReviewSummaryView {
    Long getId();
    BookView getBook();
    UserView getUser();
    Double getRating();
    String getTitle();
    Boolean getRecommendation();
    String getCommentary();
    ReviewStatus getReviewStatus();

    interface BookView { Long getId(); String getName(); }
    interface UserView { Long getId(); String getUsername(); }
}
