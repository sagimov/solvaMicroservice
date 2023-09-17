package com.example.solva.controllers;


import com.example.solva.service.TransactionService;
import com.example.solva.dto.transaction.TransLimDTO;
import com.example.solva.dto.transaction.TransactioRequest;
import com.example.solva.dto.transaction.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{account}")
    public List<TransactionResponse> getAllTransaction(@PathVariable String account) {
        return transactionService.getByAccount(account);
    }

    @PostMapping
    public TransactionResponse create(@RequestBody TransactioRequest request) {
        return transactionService.create(request);
    }

    @GetMapping(value = "/limitExceeded/{account}")
    public List<TransLimDTO> getAllExceededLimitTransactions(@PathVariable String account) {
        return transactionService.getAllExceededLimitTransactions(account);
    }
}
