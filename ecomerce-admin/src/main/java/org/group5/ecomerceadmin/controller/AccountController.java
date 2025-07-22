package org.group5.ecomerceadmin.controller;

import jakarta.servlet.http.HttpSession;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "/login"})
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        Account account = accountService.authenticate(username, password);

        if (account == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        if (account.getRole() == Role.ADMIN) {
            session.setAttribute("user", account);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "You are not authorized to access this function!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/accounts")
    public String listCustomerAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllCustomerAccounts());
        return "user-list";
    }

    @GetMapping("/accounts/create")
    public String showCreateForm(Model model) {
        Account account = new Account();
        account.setRole(Role.CUSTOMER); // Gán trước khi truyền vào model
        model.addAttribute("account", account);
        return "user-add";
    }


    @PostMapping("/accounts/save")
    public String saveAccount(@ModelAttribute("account") Account account) {
        accountService.saveAccount(account);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        if (account == null) return "redirect:/accounts";
        model.addAttribute("account", account);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @GetMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        accountService.toggleAccountStatus(id);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/search")
    public String searchAccounts(@RequestParam("keyword") String keyword, Model model) {
        List<Account> results = accountService.searchByUsername(keyword);
        model.addAttribute("accounts", results);
        model.addAttribute("keyword", keyword);
        return "user-list";
    }
}