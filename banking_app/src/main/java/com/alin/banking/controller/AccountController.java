package com.alin.banking.controller;

import com.alin.banking.model.Account;
import com.alin.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Map<String,String> request){
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String cnp = request.get("cnp");
        String email = request.get("email");
        String address = request.get("address");
        return accountService.createAccount(firstName,lastName,cnp,email,address);
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
