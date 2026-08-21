package com.alin.banking.controller;

import com.alin.banking.model.Account;
import com.alin.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestParam String numar_cont,
                                 @RequestParam String nume_detinator,
                                 @RequestParam Double balanta){
        return accountService.createAccount(numar_cont,nume_detinator,balanta);
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
}
