package org.group5.ecomerceadmin.config.security;

import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findByUsername(username);
        if (acc == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return User.withUsername(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole().name())
                .build();
    }
}
