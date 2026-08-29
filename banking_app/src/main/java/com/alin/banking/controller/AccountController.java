package com.alin.banking.controller;

import com.alin.banking.dto.AccountResponseDTO;
import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.model.Account;
import com.alin.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Bank account management operations")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create a new account for the authenticated user")
    @PostMapping
    public AccountResponseDTO createAccount(){
        return accountService.createAccount();
    }

    @Operation(summary = "List all accounts of the authenticated user")
    @GetMapping
    public List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Find account by IBAN number(own or ADMIN")
    @GetMapping("/number/{accountnumber}")
    public AccountResponseDTO getAccountByAccountNumber(@PathVariable String accountnumber){
        return accountService.getAccountByAccountNumber(accountnumber);
    }

    @Operation(summary = "List all accounts in the system (ADMIN only)")
    @GetMapping("/admin/all")
    public List<AccountResponseDTO> getAllAccountsForAdmin(){return accountService.getAllAccountsForAdmin();}

    @Operation(summary = "Delete an account (balance 0, owner or ADMIN)")
    @DeleteMapping("/{accountNumber}")
    public String deleteAccount(@PathVariable String accountNumber){
         accountService.deleteAccount(accountNumber);
         return "Contul a fost sters";
    }
}
