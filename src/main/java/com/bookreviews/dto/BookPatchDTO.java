package com.bookreviews.dto;

import java.util.List;

public record BookPatchDTO(String name, String author,
                           List<Long> genreIds) {}
