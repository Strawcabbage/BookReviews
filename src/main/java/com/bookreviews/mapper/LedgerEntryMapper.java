package com.bookreviews.mapper;

import com.bookreviews.dto.LedgerEntryDTO;
import com.bookreviews.entity.LedgerEntry;

public class LedgerEntryMapper {

    public LedgerEntryDTO toDto(LedgerEntry entry) {

        return new LedgerEntryDTO(
                entry.getAccount().getId(),
                entry.getAmount(),
                entry.getType(),
                entry.getDescription(),
                entry.getCreatedAt()
                );

    }

}
