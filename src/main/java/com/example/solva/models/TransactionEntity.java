package com.example.solva.models;

import com.example.solva.dto.transaction.TransactionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_table")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "account_from")
    private String accountFrom;

    @Column(name = "account_to")
    private String accountTo;

    @Column(name = "currency_shortname")
    private String currencyShortname;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "expense_category")
    private String category;

    @Column(name = "datetime")
    private String dateTime;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "limit_id",referencedColumnName = "limit_id")
    @Fetch(FetchMode.JOIN)
    LimitEntity limitEntity;


    public TransactionEntity(String accountFrom, String accountTo, String currencyShortname, BigDecimal sum, String category, String dateTime, boolean limitExceeded, LimitEntity limitEntity) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyShortname = currencyShortname;
        this.sum = sum;
        this.category = category;
        this.dateTime = dateTime;
        this.limitExceeded = limitExceeded;
        this.limitEntity = limitEntity;
    }

    public TransactionResponse toDTO() {
        return new TransactionResponse(
                this.transactionId,
                this.accountFrom,
                this.accountTo,
                this.currencyShortname,
                this.sum.doubleValue(),
                this.category,
                this.dateTime
        );
    }

}
