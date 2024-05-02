package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {

    @Autowired
  private AccountRepository accountRepository;


  // user registration
  public Account registerAccount(Account account) {
    if (isValidRegistration(account)) {
      Account savedAccount = accountRepository.save(account);
      return savedAccount;
    } else {
      throw new IllegalArgumentException("Invalid registration details");
    }
  }

  private boolean isValidRegistration(Account account) {
    if (account.getUsername() == null || account.getUsername().isBlank()) {
      return false;
    }
    if (account.getPassword() == null || account.getPassword().length() < 4) {
      return false;
    }
    return !accountRepository.findByUsername(account.getUsername()).isPresent();
  }


  // user login
  public Account login(String username, String password) {
    Optional<Account> optionalAccount = accountRepository.findByUsername(username);
    if (optionalAccount.isPresent()) {
      Account account = optionalAccount.get();
      if (account.getPassword().equals(password)) {
        return account;
      }
    }
    return null;
  }

}


