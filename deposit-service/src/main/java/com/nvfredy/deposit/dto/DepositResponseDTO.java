package com.nvfredy.deposit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResponseDTO {

    private BigDecimal amount;
    private String email;

    public DepositResponseDTO(BigDecimal amount, String email) {
        this.amount = amount;
        this.email = email;
    }
}
