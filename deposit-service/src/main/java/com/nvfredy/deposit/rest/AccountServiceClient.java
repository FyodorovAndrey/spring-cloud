package com.nvfredy.deposit.rest;

import com.nvfredy.deposit.dto.AccountResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/{accountId}")
    AccountResponseDTO getAccountById(@PathVariable Long accountId);
}
