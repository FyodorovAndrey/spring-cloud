package com.nvfredy.deposit.rest;

import com.nvfredy.deposit.dto.BillRequestDTO;
import com.nvfredy.deposit.dto.BillResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @GetMapping("/bills/{billId}")
    BillResponseDTO getBillById(@PathVariable Long billId);

    @PutMapping("/bills/{billId}")
    void update(@PathVariable Long billId, @RequestBody BillRequestDTO requestDTO);

    @GetMapping("/bills/account/{accountId}")
    List<BillResponseDTO> getBillsByAccountId(@PathVariable Long accountId);
}
