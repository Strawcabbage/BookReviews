package com.example.bookreviews.dto;

import java.util.List;

public record BookDTO(
        Long id,
        String name,
        String author,
        Double rating,
        Long reviewCount,
        List<String> genres
) {}