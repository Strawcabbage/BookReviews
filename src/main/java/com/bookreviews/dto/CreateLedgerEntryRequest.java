package com.bookreviews.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record CreateLedgerEntryRequest(

        @NotBlank Long userAccount_id,
        @NotBlank Long amount,
        @NotBlank String description

) {}
