package com.example.solva.service.impl;

import com.example.solva.models.TransactionEntity;
import com.example.solva.service.TransactionService;
import com.example.solva.store.LimitRepository;
import com.example.solva.store.TransactionRepository;
import com.example.solva.dto.transaction.TransactioRequest;
import com.example.solva.dto.transaction.TransLimDTO;
import com.example.solva.dto.transaction.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;
    private final LimitServiceImpl limitService;

    @Override
    public List<TransLimDTO> getAllExceededLimitTransactions(String account) {
        return transactionRepository.fetchTranLimDataInnerJoin(account);
    }

    @Override
    public List<TransactionResponse> getByAccount(String account) {
        return transactionRepository.findAllByAccountFrom(account).stream().map(TransactionEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionResponse create(TransactioRequest saveTransactionDTO) {
        return transactionRepository.saveAndFlush(new TransactionEntity(
                saveTransactionDTO.getAccountFrom(),
                saveTransactionDTO.getAccountTo(),
                saveTransactionDTO.getCurrencyShortname(),
                BigDecimal.valueOf(saveTransactionDTO.getSum()),
                saveTransactionDTO.getCategory(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault()).format(new Date()),
                limitService.updateLimitBalance(saveTransactionDTO.getAccountFrom(), saveTransactionDTO.getCategory(), saveTransactionDTO.getSum(), saveTransactionDTO.getCurrencyShortname()),
                limitRepository.findFirstByUserAccountAndLimitCategoryOrderByLimitSettingDateDesc(saveTransactionDTO.getAccountFrom(), saveTransactionDTO.getCategory())
        )).toDTO();

    }
}
