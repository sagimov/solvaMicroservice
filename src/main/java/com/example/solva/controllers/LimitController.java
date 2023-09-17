package com.example.solva.controllers;


import com.example.solva.service.impl.LimitServiceImpl;
import com.example.solva.dto.limit.LimitResponse;
import com.example.solva.dto.limit.LimitRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/limits")
@AllArgsConstructor
public class LimitController {

    private final LimitServiceImpl limitService;

    @GetMapping(value = "/{account}")
    public List<LimitResponse> getAccountLimits(@PathVariable String account) {
        return limitService.getAll(account);
    }

    @PostMapping
    public LimitResponse create(@RequestBody LimitRequest request) {
        return limitService.create(request);
    }

    @PutMapping
    public LimitResponse update(@RequestBody LimitRequest request) {
        return limitService.update(request);
    }
}
