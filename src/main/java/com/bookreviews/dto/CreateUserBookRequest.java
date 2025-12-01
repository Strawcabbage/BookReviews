package com.bookreviews.dto;

import com.bookreviews.entity.ReadStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateUserBookRequest(

        @NotBlank ReadStatus readStatus,
        @NotBlank @Min(0) @Max(100) Integer percentRead,
        @NotBlank Long bookId

) {}
