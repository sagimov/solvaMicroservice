package com.example.solva.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactioRequest {
    private String accountFrom;

    private String accountTo;

    private String currencyShortname;

    private Double sum;

    private String category;
}
