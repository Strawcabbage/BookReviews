package com.bookreviews.dto;

import com.bookreviews.entity.ReviewStatus;
import jakarta.validation.constraints.NotBlank;

public record ReviewModerationDTO(@NotBlank ReviewStatus reviewStatus) {
}
