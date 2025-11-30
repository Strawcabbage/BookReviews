package com.bookreviews.entity;

import java.util.List;

public interface UserSummaryList {

    Long getId();
    String getEmail();
    String getUsername();
    Integer getGenreCount();
    Integer getUserBookCount();
    Boolean getAdmin();

}
