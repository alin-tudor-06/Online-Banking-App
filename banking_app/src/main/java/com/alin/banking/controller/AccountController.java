package com.alin.banking.controller;

import com.alin.banking.dto.AccountResponseDTO;
import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.model.Account;
import com.alin.banking.service.AccountService;
import jakarta.validation.Valid;
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
    public AccountResponseDTO createAccount(@Valid @RequestBody UserCreateDTO dto){
        return accountService.createAccount(dto);
    }

    @GetMapping
    public List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/number/{accountnumber}")
    public AccountResponseDTO getAccountByAccountNumber(@PathVariable String accountnumber){
        return accountService.getAccountByAccountNumber(accountnumber);
    }
}
