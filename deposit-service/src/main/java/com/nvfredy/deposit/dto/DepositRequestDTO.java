package com.nvfredy.deposit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDTO {

    private Long accountId;
    private Long billId;
    private BigDecimal amount;
}
