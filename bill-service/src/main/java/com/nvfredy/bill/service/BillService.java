package com.nvfredy.bill.service;

import com.nvfredy.bill.entity.Bill;
import com.nvfredy.bill.exception.BillNotFoundException;
import com.nvfredy.bill.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId).orElseThrow(() -> new BillNotFoundException("Bill with id: " + billId + " not found"));
    }

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        Bill bill = new Bill();
        bill.setAccountId(accountId);
        bill.setAmount(amount);
        bill.setIsDefault(isDefault);
        bill.setOverdraftEnabled(overdraftEnabled);
        bill.setCreationDate(OffsetDateTime.now());
        return billRepository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            bill.get().setAccountId(accountId);
            bill.get().setAmount(amount);
            bill.get().setIsDefault(isDefault);
            bill.get().setOverdraftEnabled(overdraftEnabled);
            billRepository.save(bill.get());
        } else {
            createBill(accountId, amount, isDefault, overdraftEnabled);
        }
        return bill.get();
    }

    public Bill deleteBill(Long billId) {
        Bill deletedBill = getBillById(billId);
        billRepository.deleteById(billId);
        return deletedBill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return billRepository.findByAccountId(accountId);
    }
}
