package com.bookreviews.dto;

import com.bookreviews.entity.LedgerType;

import java.time.Instant;

public record LedgerEntryDTO(

        Long UserAccountID,
        Long amount,
        LedgerType type,
        String description,
        Instant createdAt

) {}
