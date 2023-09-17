package com.example.solva.service;

import com.example.solva.dto.transaction.TransLimDTO;
import com.example.solva.dto.transaction.TransactioRequest;
import com.example.solva.dto.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransLimDTO> getAllExceededLimitTransactions(String account);

    List<TransactionResponse> getByAccount(String account);

    TransactionResponse create(TransactioRequest saveTransactionDTO);
}
