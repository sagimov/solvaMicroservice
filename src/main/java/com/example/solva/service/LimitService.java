package com.example.solva.service;

import com.example.solva.dto.limit.LimitResponse;
import com.example.solva.dto.limit.LimitRequest;

import java.util.List;

public interface LimitService {

    List<LimitResponse> getAll(String userAccount);

    LimitResponse create(LimitRequest initLimitDTO);

    LimitResponse update(LimitRequest updateLimitDTO);

    boolean updateLimitBalance(String account, String category, Double sum, String currencyShortname);
}
