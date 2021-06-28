package com.nvfredy.deposit.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositId;
    private BigDecimal amount;
    private Long billId;
    private OffsetDateTime depositDate;
    private String email;

    public Deposit(BigDecimal amount, Long billId, OffsetDateTime depositDate, String email) {
        this.amount = amount;
        this.billId = billId;
        this.depositDate = depositDate;
        this.email = email;
    }
}
