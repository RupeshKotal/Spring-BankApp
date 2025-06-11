package com.bankapp.account.controller;

import com.bankapp.account.dto.AccountDTO;
import com.bankapp.account.model.Account;
import com.bankapp.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "APIs for bank account management")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all accounts for a user")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/{accountNumber}")
    @Operation(summary = "Get account by account number")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get account by ID")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
} 