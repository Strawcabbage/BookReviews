package com.bookreviews.dto;

import com.bookreviews.entity.Book;
import com.bookreviews.entity.ReviewStatus;
import com.bookreviews.entity.User;

public record ReviewDTO(
        Long id,
        Long book_id,
        String book_name,
        Double rating,
        Long user_id,
        String username,
        String title,
        Boolean recommendation,
        String commentary,
        ReviewStatus reviewStatus
) {}
