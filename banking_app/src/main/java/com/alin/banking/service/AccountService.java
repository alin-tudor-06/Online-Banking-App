package com.alin.banking.service;

import com.alin.banking.model.Account;
import com.alin.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String numar_cont,String nume_detinator,Double balanta){
        Account cont_nou =  new Account(numar_cont,nume_detinator,balanta);
        accountRepository.save(cont_nou);
        return cont_nou;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contul nu a fost gasit!"));
    }

}
