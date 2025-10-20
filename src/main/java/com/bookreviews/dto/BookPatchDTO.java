package com.bookreviews.dto;

import java.util.List;

public record BookPatchDTO(String name, String author, String publish_date,
                           List<Long> genreIds) {}
