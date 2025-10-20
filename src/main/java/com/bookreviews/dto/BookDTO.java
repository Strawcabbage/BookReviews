package com.bookreviews.dto;

import java.util.List;

public record BookDTO(
        Long id,
        String name,
        String author,
        String publish_date,
        Double rating,
        Long reviewCount,
        List<String> genres
) {}