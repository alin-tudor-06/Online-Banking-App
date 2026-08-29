package com.alin.banking.service;

import com.alin.banking.dto.AccountResponseDTO;
import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.model.Account;
import com.alin.banking.model.Role;
import com.alin.banking.model.User;
import com.alin.banking.repository.AccountRepository;
import com.alin.banking.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;

    private String generateAccountNumber(){
        long number = ThreadLocalRandom.current().nextLong(100000000);
        return "AL" + String.format("%08d",number);
    }

    private AccountResponseDTO convertToDto(Account account){
        return new AccountResponseDTO(
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency(),
                account.getUser().getFirstName(),
                account.getUser().getLastName()
        );
    }

    public Account findAccountByNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Contul cu numarul " + accountNumber + " nu a fost gasit"));
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException("Utilizatorul nu este autentificat");
        }

        Object principal = authentication.getPrincipal();

        if(principal instanceof CustomUserDetails){
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getUser();
        }

        throw new RuntimeException("Utilizatorul nu a putut fi identificat");
    }

    public AccountResponseDTO createAccount() {
        User user = getCurrentUser();

        String accountNumber = generateAccountNumber();
        Account newAccount = new Account(accountNumber, BigDecimal.ZERO, "RON", user);
        Account saved = accountRepository.save(newAccount);
        return convertToDto(saved);
    }

    public List<AccountResponseDTO> getAllAccounts(){
        return accountRepository.findByUser(getCurrentUser()).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponseDTO> getAllAccountsForAdmin(){
        return accountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AccountResponseDTO getAccountByAccountNumber(String accountnumber){
        Account account = accountRepository.findByAccountNumber(accountnumber).orElseThrow(() -> new RuntimeException("Contul cu numarul " + accountnumber + " nu a fost gasit"));

        User user = getCurrentUser();
        if(!user.getCnp().equals(account.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea sa accesati acest cont");
        }
        return convertToDto(account);
    }

    public void deleteAccount(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Contul cu IBAN-ul " + accountNumber + " nu a fost gasit"));

        User user = getCurrentUser();
        if(!user.getCnp().equals(account.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea sa stergeti acest cont");
        }

        if(account.getBalance().compareTo(BigDecimal.ZERO) != 0){
            throw new RuntimeException("Contul nu poate fi șters deoarece are sold pozitiv");
        }
        accountRepository.delete(account);
    }
}
