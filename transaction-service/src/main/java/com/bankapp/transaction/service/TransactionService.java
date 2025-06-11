package com.bankapp.transaction.service;

import com.bankapp.transaction.dto.TransactionDTO;
import com.bankapp.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionDTO deposit(Transaction transaction);
    TransactionDTO withdraw(Transaction transaction);
    TransactionDTO transfer(Transaction transaction);
    List<TransactionDTO> getTransactionsByAccountId(Long accountId);
} 