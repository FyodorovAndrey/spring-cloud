package com.nvfredy.deposit.dto;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class AccountResponseDTO {

    private Long accountId;
    private String name;
    private String email;
    private String phone;
    private OffsetDateTime creationDate;

}
