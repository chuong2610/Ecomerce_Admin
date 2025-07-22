package org.group5.ecomerceadmin.service;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.group5.ecomerceadmin.enums.Role;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account authenticate(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if(account == null || !account.getPassword().equals(password)) {
            return null;
        }
        return account;
    }

    public List<Account> getAllCustomerAccounts() {
        return accountRepository.findByRole(Role.CUSTOMER);
    }

    public List<Account> searchByUsername(String keyword) {
        return accountRepository.findByUsernameContainingIgnoreCase(keyword);
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void toggleAccountStatus(Long id) {
        Account acc = accountRepository.findById(id).orElse(null);
        if (acc != null) {
            acc.setActive(!acc.isActive());
            accountRepository.save(acc);
        }
    }




}

