package com.bookreviews.dto;

import com.bookreviews.entity.ReviewStatus;

public record AdminReviewPatchDTO(Double rating, String title,
                                  Boolean recommendation, String commentary,
                                  ReviewStatus reviewStatus) {
}
