package com.bookreviews.dto;

import com.bookreviews.entity.LedgerType;

public record LedgerEntryPatchDTO(

        Long amount,
        LedgerType type,
        String description

) {}
