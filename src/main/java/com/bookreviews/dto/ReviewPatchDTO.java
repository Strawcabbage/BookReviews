package com.bookreviews.dto;

public record ReviewPatchDTO(Double rating, String title,
                             Boolean recommendation, String commentary) {
}
