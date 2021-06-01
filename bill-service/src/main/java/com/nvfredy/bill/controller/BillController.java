package com.nvfredy.bill.controller;

import com.nvfredy.bill.dto.BillRequestDto;
import com.nvfredy.bill.dto.BillResponseDto;
import com.nvfredy.bill.service.BillService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {

    private final BillService billService;


    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public BillResponseDto getBill(@PathVariable Long billId) {
        return new BillResponseDto(billService.getBillById(billId));
    }

    @PostMapping("/")
    public Long createBill(@RequestBody BillRequestDto requestDto) {
        return billService.createBill(
                requestDto.getAccountId(),
                requestDto.getAmount(),
                requestDto.getIsDefault(),
                requestDto.getOverdraftEnabled());
    }

    @PutMapping("/{billId}")
    public BillResponseDto updateBill(@PathVariable Long billId,
                                            @RequestBody BillRequestDto requestDto) {
        return new BillResponseDto(billService.updateBill(
                billId,
                requestDto.getAccountId(),
                requestDto.getAmount(),
                requestDto.getIsDefault(),
                requestDto.getOverdraftEnabled()));
    }

    @DeleteMapping("/{billId}")
    public BillResponseDto deleteBill(@PathVariable Long billId) {
        return new BillResponseDto(billService.deleteBill(billId));
    }
}
