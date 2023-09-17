package com.example.solva.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;

    private String accountFrom;

    private String accountTo;

    private String currencyShortname;

    private Double sum;

    private String category;

    private String dateTime;
}
