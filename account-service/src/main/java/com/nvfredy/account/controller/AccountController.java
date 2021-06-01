package com.nvfredy.account.controller;

import com.nvfredy.account.dto.AccountRequestDto;
import com.nvfredy.account.dto.AccountResponseDto;
import com.nvfredy.account.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(accountService.getAccountById(accountId));
    }

    @PostMapping("/")
    public Long createAccount(@RequestBody AccountRequestDto requestDto) {
        return accountService.createAccount(requestDto.getName(), requestDto.getEmail(), requestDto.getPhone(), requestDto.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable Long accountId,
                                            @RequestBody AccountRequestDto requestDto) {
        return new AccountResponseDto(accountService.updateAccount(
                accountId,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDto deleteAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(accountService.deleteAccount(accountId));
    }

}
