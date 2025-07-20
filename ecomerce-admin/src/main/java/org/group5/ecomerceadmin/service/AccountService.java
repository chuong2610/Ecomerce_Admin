package org.group5.ecomerceadmin.service;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.springframework.stereotype.Service;

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

}

