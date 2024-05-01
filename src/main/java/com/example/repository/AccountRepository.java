package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


import com.example.entity.Account;

public interface AccountRepository  extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
