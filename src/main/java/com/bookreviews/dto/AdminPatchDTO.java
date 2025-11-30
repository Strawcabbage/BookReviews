package com.bookreviews.dto;

import java.util.List;

public record AdminPatchDTO(

        String email,
        String username,
        String realName,
        String birthDate,
        List<Long> userGenreIds,
        List<Long> userBookIds,
        Boolean admin

) {}
