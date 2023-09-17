package com.example.solva.dto.limit;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitRequest {
    private String account;
    private String category;
    private Double accountLimit;
}
