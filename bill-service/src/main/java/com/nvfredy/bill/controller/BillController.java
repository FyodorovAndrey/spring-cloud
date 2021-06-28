package com.nvfredy.bill.controller;

import com.nvfredy.bill.dto.BillRequestDTO;
import com.nvfredy.bill.dto.BillResponseDTO;
import com.nvfredy.bill.entity.Bill;
import com.nvfredy.bill.service.BillService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BillController {

    private final BillService billService;


    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public BillResponseDTO getBill(@PathVariable Long billId) {
        return new BillResponseDTO(billService.getBillById(billId));
    }

    @PostMapping("/create")
    public Long createBill(@RequestBody BillRequestDTO requestDto) {
        return billService.createBill(
                requestDto.getAccountId(),
                requestDto.getAmount(),
                requestDto.getIsDefault(),
                requestDto.getOverdraftEnabled());
    }

    @PutMapping("/{billId}")
    public BillResponseDTO updateBill(@PathVariable Long billId,
                                      @RequestBody BillRequestDTO requestDto) {
        return new BillResponseDTO(billService.updateBill(
                billId,
                requestDto.getAccountId(),
                requestDto.getAmount(),
                requestDto.getIsDefault(),
                requestDto.getOverdraftEnabled()));
    }

    @DeleteMapping("/{billId}")
    public BillResponseDTO deleteBill(@PathVariable Long billId) {
        return new BillResponseDTO(billService.deleteBill(billId));
    }

    @GetMapping("/account/{accountId}")
    public List<BillResponseDTO> getBillsByAccountId(@PathVariable Long accountId) {
        return billService
                .getBillsByAccountId(accountId)
                .stream().map(BillResponseDTO::new)
                .collect(Collectors.toList());
    }
}
