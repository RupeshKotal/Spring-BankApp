package com.bankapp.transaction.dto;

import com.bankapp.transaction.model.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long accountId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private Long targetAccountId;
    private LocalDateTime createdAt;
} 