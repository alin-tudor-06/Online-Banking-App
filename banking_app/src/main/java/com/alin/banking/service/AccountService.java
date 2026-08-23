package com.alin.banking.service;

import com.alin.banking.dto.AccountResponseDTO;
import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.model.Account;
import com.alin.banking.model.User;
import com.alin.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AccountResponseDTO createAccount(UserCreateDTO dto) {
        User user;
        try {
            user = userService.findUserByCnp(dto.getCnp());
        } catch (RuntimeException e) {
            user = userService.createUserEntity(dto);
        }
        String accountNumber = generateAccountNumber();
        Account newAccount = new Account(accountNumber, BigDecimal.ZERO, "RON", user);
        Account saved = accountRepository.save(newAccount);
        return convertToDto(saved);
    }

    public List<AccountResponseDTO> getAllAccounts(){
        return accountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AccountResponseDTO getAccountById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Contul cu ID-ul " + id + " nu a fost gasit"));
        return convertToDto(account);
    }

    public AccountResponseDTO getAccountByAccountNumber(String accountnumber){
        Account account = accountRepository.findByAccountNumber(accountnumber).orElseThrow(() -> new RuntimeException("Contul cu numarul " + accountnumber + " nu a fost gasit"));
        return convertToDto(account);
    }
}
