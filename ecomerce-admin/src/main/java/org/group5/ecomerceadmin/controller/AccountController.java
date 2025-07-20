package org.group5.ecomerceadmin.controller;

import jakarta.servlet.http.HttpSession;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "/login"})
    public String showLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session){
        Account account = accountService.authenticate(username, password);

        if(account == null){
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        if (account.getRole() == Role.ADMIN) {
            session.setAttribute("user", account);
            return "/index.html";
        } else {
            model.addAttribute("error", "You are not authorized to access this function!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }


}