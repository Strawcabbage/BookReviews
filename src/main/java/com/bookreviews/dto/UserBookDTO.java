package com.bookreviews.dto;

import com.bookreviews.entity.Book;
import com.bookreviews.entity.ReadStatus;
import com.bookreviews.entity.User;

public record UserBookDTO(
        ReadStatus readStatus,
        Integer percentRead,
        Long bookId,
        Long userId
) {
}
