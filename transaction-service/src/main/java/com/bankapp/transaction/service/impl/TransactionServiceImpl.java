package com.bankapp.transaction.service.impl;

import com.bankapp.transaction.dto.TransactionDTO;
import com.bankapp.transaction.model.Transaction;
import com.bankapp.transaction.model.TransactionType;
import com.bankapp.transaction.repository.TransactionRepository;
import com.bankapp.transaction.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionDTO deposit(Transaction transaction) {
        transaction.setType(TransactionType.DEPOSIT);
        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public TransactionDTO withdraw(Transaction transaction) {
        transaction.setType(TransactionType.WITHDRAW);
        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public TransactionDTO transfer(Transaction transaction) {
        transaction.setType(TransactionType.TRANSFER);
        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTargetAccountId(),
                transaction.getCreatedAt()
        );
    }
} 