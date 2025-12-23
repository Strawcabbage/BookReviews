package com.bookreviews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name="ledger_entry")
public class LedgerEntry {

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount account;

    @Setter
    @Getter
    private long amount;

    @Setter
    @Getter
    private LedgerType type;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Instant createdAt;

}
