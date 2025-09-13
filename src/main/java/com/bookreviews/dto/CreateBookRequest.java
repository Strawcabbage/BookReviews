package com.bookreviews.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateBookRequest(
        @NotBlank String name,
        @NotBlank String author,
        @NotEmpty List<@Positive Long> genreIds
) {}