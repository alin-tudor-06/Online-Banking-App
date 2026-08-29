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
@Tag(name = "Conturi",description = "Operatiuni pentru gestionarea conturilor bancare")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Creeaza un cont nou pentru utilizatorul autentificat")
    @PostMapping
    public AccountResponseDTO createAccount(){
        return accountService.createAccount();
    }

    @Operation(summary = "Listeaza toate conturile utilizatorului autentificat")
    @GetMapping
    public List<AccountResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Cauta un cont dupa numarul IBAN (propriu sau ADMIN)")
    @GetMapping("/number/{accountnumber}")
    public AccountResponseDTO getAccountByAccountNumber(@PathVariable String accountnumber){
        return accountService.getAccountByAccountNumber(accountnumber);
    }

    @Operation(summary = "Listeaza toate conturile din sistem(doar ADMIN)")
    @GetMapping("/admin/all")
    public List<AccountResponseDTO> getAllAccountsForAdmin(){return accountService.getAllAccountsForAdmin();}

    @Operation(summary = "Sterge un cont(doar daca soldul este 0 si utilizatorul este proprietar sau ADMIN)")
    @DeleteMapping("/{accountNumber}")
    public String deleteAccount(@PathVariable String accountNumber){
         accountService.deleteAccount(accountNumber);
         return "Contul a fost sters";
    }
}
