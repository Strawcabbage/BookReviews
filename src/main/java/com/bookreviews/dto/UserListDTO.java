package com.bookreviews.dto;

import com.bookreviews.entity.UserBook;

import java.util.List;

public record UserListDTO (

        String username,
        List<UserBook> userBooks,
        Boolean admin

) {}
