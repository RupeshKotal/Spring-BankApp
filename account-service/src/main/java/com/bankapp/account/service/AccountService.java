package com.bankapp.account.service;

import com.bankapp.account.dto.AccountDTO;
import com.bankapp.account.model.Account;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(Account account);
    AccountDTO getAccountByNumber(String accountNumber);
    List<AccountDTO> getAccountsByUserId(Long userId);
    AccountDTO getAccountById(Long id);
} 