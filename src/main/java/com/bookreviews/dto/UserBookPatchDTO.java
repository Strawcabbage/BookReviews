package com.bookreviews.dto;

import com.bookreviews.entity.ReadStatus;

public record UserBookPatchDTO(

        ReadStatus readStatus,
        Integer percentRead

) {}
