package com.bankapp.transaction.controller;

import com.bankapp.transaction.dto.TransactionDTO;
import com.bankapp.transaction.model.Transaction;
import com.bankapp.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management", description = "APIs for deposit, withdraw, transfer, and transaction history")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit to account")
    public ResponseEntity<TransactionDTO> deposit(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.deposit(transaction));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw from account")
    public ResponseEntity<TransactionDTO> withdraw(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.withdraw(transaction));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer between accounts")
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.transfer(transaction));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transaction history for account")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
    }
} 