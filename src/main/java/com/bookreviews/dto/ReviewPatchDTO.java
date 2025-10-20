package com.bookreviews.dto;

import com.bookreviews.entity.Book;
import com.bookreviews.entity.ReviewStatus;
import com.bookreviews.entity.User;

public record ReviewPatchDTO(Double rating, String title,
                             Boolean recommendation, String commentary) {
}
