package com.bookreviews.dto;

import com.bookreviews.entity.Genre;
import com.bookreviews.entity.UserBook;

import java.util.List;
import java.util.Set;

public record UserDTO(

        String email,
        String username,
        Set<Genre> userGenres,
        List<UserBook> userBooks,
        Boolean admin

) {}
