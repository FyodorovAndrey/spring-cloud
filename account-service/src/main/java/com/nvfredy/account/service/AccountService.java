package com.nvfredy.account.service;

import com.nvfredy.account.entity.Account;
import com.nvfredy.account.exception.AccountNotFoundException;
import com.nvfredy.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException( "Account with id : " + id + " not found."));
    }

    public Long createAccount(String name, String email, String phone, List<Long> bills) {
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setPhone(phone);
        account.setBills(bills);
        account.setCreationDate(OffsetDateTime.now());
        accountRepository.save(account);
        return account.getAccountId();
    }

    public Account updateAccount(Long id, String name, String email, String phone, List<Long> bills) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            account.get().setName(name);
            account.get().setEmail(email);
            account.get().setPhone(phone);
            account.get().setBills(bills);
            accountRepository.save(account.get());
        } else {
            createAccount(name, email, phone, bills);
        }
        return account.get();
    }

    public Account deleteAccount(Long id) {
        Account deletedAccount = getAccountById(id);
        accountRepository.deleteById(id);
        return deletedAccount;
    }
}
