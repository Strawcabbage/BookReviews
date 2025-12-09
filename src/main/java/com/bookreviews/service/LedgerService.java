package com.bookreviews.service;

import com.bookreviews.dto.CreateLedgerEntryRequest;
import com.bookreviews.dto.LedgerEntryDTO;
import com.bookreviews.dto.LedgerEntryPatchDTO;
import com.bookreviews.entity.LedgerEntry;
import com.bookreviews.entity.LedgerType;
import com.bookreviews.entity.UserAccount;
import com.bookreviews.mapper.LedgerEntryMapper;
import com.bookreviews.repository.AccountRepository;
import com.bookreviews.repository.LedgerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final AccountRepository accountRepository;
    private final LedgerEntryMapper ledgerEntryMapper;

    public LedgerService(LedgerRepository ledgerRepository, AccountRepository accountRepository, LedgerEntryMapper entryMapper) {
        this.ledgerRepository = ledgerRepository;
        this.accountRepository = accountRepository;
        this.ledgerEntryMapper = entryMapper;
    }

    @Transactional
    public LedgerEntryDTO rewardForReview(Long userId, Long reviewId, long points) {
        return credit(new CreateLedgerEntryRequest(
                userId,
                points,
                "Reward for review " + reviewId
        ));
    }

    @Transactional
    public LedgerEntryDTO debit(Long userId, long amount,
                                LedgerType type, String description) {
        UserAccount account = accountRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account " + userId + " not found"));

        long newBalance = account.getBalance() - amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        LedgerEntry entry = new LedgerEntry();
        entry.setAccount(account);
        entry.setAmount(-amount);
        entry.setType(type);
        entry.setDescription(description);
        entry.setCreatedAt(Instant.now());
        ledgerRepository.save(entry);

        account.setBalance(newBalance);

        return ledgerEntryMapper.toDto(entry);
    }

    public LedgerEntryDTO updatePartial(Long id, LedgerEntryPatchDTO patch) {

        LedgerEntry existing = ledgerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ledger " + id + " not found"));

        if (patch.amount() != null) {
            existing.setAmount(patch.amount());
        }
        if (patch.description() != null && !patch.description().isBlank()) {
            existing.setDescription(patch.description());
        }
        if (patch.type() != null) {
            existing.setType(LedgerType.ADJUSTMENT);
        }

        return ledgerEntryMapper.toDto(ledgerRepository.save(existing));

    }

    public void delete(Long id) {

        ledgerRepository.delete(ledgerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ledger " + id + " not found")));

    }

    @Transactional
    public LedgerEntryDTO credit(CreateLedgerEntryRequest request) {

        UserAccount u = accountRepository.findById(request.userAccount_id())
                .orElseThrow(() -> new EntityNotFoundException("User Account " + request.userAccount_id() + " not found"));

        LedgerEntry entry = new LedgerEntry();
        entry.setAccount(u);
        entry.setAmount(request.amount());
        entry.setType(LedgerType.REVIEW_REWARD);
        entry.setDescription(request.description());
        entry.setCreatedAt(Instant.now());

        return ledgerEntryMapper.toDto(ledgerRepository.save(entry));

    }

    public LedgerEntryDTO getOneDto(Long id) {

        return ledgerEntryMapper.toDto(ledgerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ledger " + id + " not found")));

    }

}
