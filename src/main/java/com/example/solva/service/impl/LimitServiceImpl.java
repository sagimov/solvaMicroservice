package com.example.solva.service.impl;

import com.example.solva.exception.ResourceNotFoundException;
import com.example.solva.models.CurrencyEntity;
import com.example.solva.models.LimitEntity;
import com.example.solva.service.LimitService;
import com.example.solva.store.CurrencyRepository;
import com.example.solva.store.LimitRepository;
import com.example.solva.dto.limit.LimitResponse;
import com.example.solva.dto.limit.LimitRequest;
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
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    public List<LimitResponse> getAll(String userAccount) {
        return limitRepository.findAllCategoryLimitsByUserAccount(userAccount).stream().map(LimitEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public LimitResponse create(LimitRequest request) {
        return limitRepository.save(new LimitEntity(
                request.getAccount(),
                request.getCategory(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault()).format(new Date()),
                BigDecimal.valueOf(0.0),
                BigDecimal.valueOf(0.0)
        )).toDTO();
    }

    @Override
    @Transactional
    public LimitResponse update(LimitRequest request) {
        LimitEntity limitEntity = limitRepository.findFirstByUserAccountAndLimitCategoryOrderByLimitSettingDateDesc(request.getAccount(), request.getCategory());
        if (limitEntity != null) {
            return limitRepository.saveAndFlush(new LimitEntity(
                    limitEntity.getUserAccount(),
                    limitEntity.getLimitCategory(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX", Locale.getDefault()).format(new Date()),
                    BigDecimal.valueOf(request.getAccountLimit()),
                    BigDecimal.valueOf(request.getAccountLimit() - limitEntity.getAccountLimit().doubleValue() + limitEntity.getLimitBalance().doubleValue())
            )).toDTO();
        } else {
            throw new ResourceNotFoundException("There is no such Account");
        }
    }

    @Override
    public boolean updateLimitBalance(String account, String category, Double sum, String currencyShortname) {
        CurrencyEntity currentCurrency = currencyRepository.getReferenceById(currencyShortname);
        LimitEntity limitEntity = limitRepository.findFirstByUserAccountAndLimitCategoryOrderByLimitSettingDateDesc(account, category);
        if (limitEntity != null) {
            limitEntity.setLimitBalance(BigDecimal.valueOf(limitEntity.getLimitBalance().doubleValue() - (sum / currentCurrency.getClose())));
            return limitRepository.saveAndFlush(limitEntity).getLimitBalance().doubleValue() < 0;
        } else {
            throw new ResourceNotFoundException("There is no such Account");
        }
    }

}
