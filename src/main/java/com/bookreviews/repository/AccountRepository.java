package com.bookreviews.repository;

import com.bookreviews.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserAccount, Long> {



}
