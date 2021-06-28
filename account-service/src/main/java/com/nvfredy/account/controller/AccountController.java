package com.nvfredy.account.controller;

import com.nvfredy.account.dto.AccountRequestDTO;
import com.nvfredy.account.dto.AccountResponseDTO;
import com.nvfredy.account.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDTO getAccount(@PathVariable Long accountId) {
        return new AccountResponseDTO(accountService.getAccountById(accountId));
    }

    @PostMapping("/create")
    public Long createAccount(@RequestBody AccountRequestDTO requestDto) {
        return accountService.createAccount(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDTO updateAccount(@PathVariable Long accountId,
                                            @RequestBody AccountRequestDTO requestDto) {
        return new AccountResponseDTO(accountService.updateAccount(
                accountId,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDTO deleteAccount(@PathVariable Long accountId) {
        return new AccountResponseDTO(accountService.deleteAccount(accountId));
    }

}
