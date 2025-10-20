package com.bookreviews.dto;

public record BookListDTO(
        Long id,
        String name,
        String author,
        String publishDate,
        double avgRating,
        long reviewCount
) {}
