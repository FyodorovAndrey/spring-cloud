package com.nvfredy.bill.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BillRequestDto {

    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
}
