package com.bookreviews.repository;

import com.bookreviews.entity.LedgerEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {

    Page<LedgerEntry> findByAccountIdOrderByCreatedAtDesc(Long accountId, Pageable pageable);

    @Query("""
        select coalesce(sum(e.amount), 0)
        from LedgerEntry e
        where e.account.id = :accountId
    """)
    long sumAmountByAccountId(@Param("accountId") Long accountId);

    Page<LedgerEntry> findByAccountUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

}
