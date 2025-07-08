package org.group5.ecomerceadmin.controller;

import org.group5.ecomerceadmin.config.security.JwtUtil;
import org.group5.ecomerceadmin.dto.request.*;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private final AccountRepository accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AccountController(AccountRepository accountRepo,
                             PasswordEncoder passwordEncoder,
                             AuthenticationManager authManager,
                             JwtUtil jwtUtil) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(Role.valueOf(request.getRole().toUpperCase()));
        account.setFullName(request.getFullName());
        accountRepo.save(account);
        return "Register success";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        Account account = accountRepo.findByUsername(request.getUsername());
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại");
        }

        String token = jwtUtil.generateToken(account.getUsername(), account.getRole().name());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUsername(account.getUsername());
        response.setRole(account.getRole().name());
        return response;
    }
}
