package com.nvfredy.account.dto;

import com.nvfredy.account.entity.Account;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class AccountResponseDTO {

    private Long accountId;
    private String name;
    private String email;
    private String phone;
    private OffsetDateTime creationDate;

    public AccountResponseDTO(Account account) {
        accountId = account.getAccountId();
        name = account.getName();
        email = account.getEmail();
        phone = account.getPhone();
        creationDate = account.getCreationDate();
    }
}
