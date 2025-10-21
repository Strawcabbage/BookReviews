package com.bookreviews.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateReviewRequest(
        @NotBlank Long book_id,
        @NotBlank @Min(1) @Max(5) Double rating,
        @NotBlank Long user_id,
        @NotBlank Boolean recommendation,
        @NotBlank String title,
        @NotBlank String commentary
) {}
