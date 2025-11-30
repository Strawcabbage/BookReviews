package com.bookreviews.dto;

import com.bookreviews.entity.Genre;
import com.bookreviews.entity.UserBook;

import java.util.List;
import java.util.Set;

public record UserPatchDTO(

        String email,
        String username,
        String realName,
        String birthDate,
        List<Long> userGenreIds,
        List<Long> userBookIds

) {}
