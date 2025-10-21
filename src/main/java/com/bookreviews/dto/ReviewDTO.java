package com.bookreviews.dto;

import com.bookreviews.entity.ReviewStatus;

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
