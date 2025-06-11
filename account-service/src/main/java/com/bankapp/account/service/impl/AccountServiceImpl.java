package com.bankapp.account.service.impl;

import com.bankapp.account.dto.AccountDTO;
import com.bankapp.account.model.Account;
import com.bankapp.account.repository.AccountRepository;
import com.bankapp.account.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AccountDTO createAccount(Account account) {
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(account.getBalance() == null ? java.math.BigDecimal.ZERO : account.getBalance());
        Account saved = accountRepository.save(account);
        return convertToDTO(saved);
    }

    @Override
    public AccountDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return convertToDTO(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return convertToDTO(account);
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getUserId(),
                account.getAccountNumber(),
                account.getType(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
} 