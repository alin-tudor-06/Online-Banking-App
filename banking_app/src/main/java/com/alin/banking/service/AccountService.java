package com.alin.banking.service;

import com.alin.banking.model.Account;
import com.alin.banking.model.User;
import com.alin.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


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

    public Account createAccount(String firstName,String lastName,String cnp,String email,String address){
        User user;
        try {
            user = userService.findByCnp(cnp);
        }   catch (RuntimeException e){
            user = userService.createUser(firstName,lastName,cnp,email,address);
        }
        String account_number = generateAccountNumber();
        Account new_cont =  new Account(account_number, BigDecimal.ZERO,"RON",user);
        accountRepository.save(new_cont);
        return new_cont;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Contul cu ID-ul " + id + " nu a fost gasit"));
    }
}
