package com.nvfredy.account.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AccountRequestDTO {

    private String name;
    private String email;
    private String phone;
    private List<Long> bills;
}
