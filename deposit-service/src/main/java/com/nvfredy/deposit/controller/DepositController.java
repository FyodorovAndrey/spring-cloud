package com.nvfredy.deposit.controller;

import com.nvfredy.deposit.dto.DepositRequestDTO;
import com.nvfredy.deposit.dto.DepositResponseDTO;
import com.nvfredy.deposit.service.DepositService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposits")
    public DepositResponseDTO deposit(@RequestBody DepositRequestDTO depositRequestDTO) {
        return depositService.deposit(depositRequestDTO.getAccountId(),
                depositRequestDTO.getBillId(), depositRequestDTO.getAmount());
    }
}
