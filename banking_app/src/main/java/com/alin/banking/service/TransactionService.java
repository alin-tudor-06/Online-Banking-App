package com.alin.banking.service;

import com.alin.banking.dto.TransactionRequestDTO;
import com.alin.banking.dto.TransactionResponseDTO;
import com.alin.banking.model.*;
import com.alin.banking.repository.AccountRepository;
import com.alin.banking.repository.TransactionRepository;
import com.alin.banking.security.CustomUserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private TransactionResponseDTO converToDto(Transaction transaction){
        String fromAccountNumber = null;
        String toAccountNumber = null;
        String fromOwnerName = null;
        String toOwnerName = null;

        if(transaction.getFromAccount() != null){
            fromAccountNumber = transaction.getFromAccount().getAccountNumber();
            fromOwnerName = transaction.getFromAccount().getUser().getFirstName() + " " + transaction.getFromAccount().getUser().getLastName();
        }

        if(transaction.getToAccount() != null){
            toAccountNumber=transaction.getToAccount().getAccountNumber();
            toOwnerName=transaction.getToAccount().getUser().getFirstName() + " " + transaction.getToAccount().getUser().getLastName();
        }

        return new TransactionResponseDTO(fromAccountNumber,toAccountNumber,fromOwnerName,toOwnerName,transaction.getAmount(),transaction.getType().name(),transaction.getStatus().name(),transaction.getTimestamp());
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

    @Transactional
    public TransactionResponseDTO deposit(TransactionRequestDTO dto){

        if (dto.getToAccountNumber() == null) {
            throw new RuntimeException("Pentru depunere, trebuie specificat IBAN-ul contului destinație (toAccountNumber)");
        }

        Account toAccount = accountService.findAccountByNumber(dto.getToAccountNumber());

        User user = getCurrentUser();
        if(!user.getCnp().equals(toAccount.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de a depune bani in acest cont");
        }


        Transaction transaction = new Transaction(null,toAccount,dto.getAmount(), TransactionType.DEPOSIT,TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));
        accountRepository.save(toAccount);

        return converToDto(transaction);
    }

    @Transactional
    public TransactionResponseDTO withdraw(TransactionRequestDTO dto){
        if(dto.getFromAccountNumber() == null){
            throw new RuntimeException("Pentru retragere, trebuie specificat IBAN-ul contului sursa (fromAccountNumber)");
        }

        Account fromAccount = accountService.findAccountByNumber(dto.getFromAccountNumber());

        User user = getCurrentUser();
        if(!user.getCnp().equals(fromAccount.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de a retrage bani din acest cont");
        }


        if(fromAccount.getBalance().compareTo(dto.getAmount()) <0){
            throw new RuntimeException("Fonduri insuficiente");
        }

        Transaction transaction = new Transaction(fromAccount,null,dto.getAmount(),TransactionType.WITHDRAWAL,TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
        accountRepository.save(fromAccount);

        return converToDto(transaction);
    }

    public TransactionResponseDTO transfer(TransactionRequestDTO dto){
        if(dto.getFromAccountNumber() == null || dto.getToAccountNumber() == null){
            throw new RuntimeException("Pentru transfer,trebuie specificat atat IBAN-ul contului sursa (fromAccountNumber),cat si IBAN-ul contului destinație (toAccountNumber)");
        }
        if(dto.getToAccountNumber().equals(dto.getFromAccountNumber())){
            throw new RuntimeException("Pentru transfer,IBAN-ul contului destinatie trebuie sa fie diferit de cel al contului sursa");
        }

        Account fromAccount = accountService.findAccountByNumber(dto.getFromAccountNumber());
        Account toAccount= accountService.findAccountByNumber(dto.getToAccountNumber());

        User user = getCurrentUser();
        if(!user.getCnp().equals(fromAccount.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de a transfera bani din acest cont");
        }


        if(fromAccount.getBalance().compareTo(dto.getAmount()) <0){
            throw new RuntimeException("Fonduri insuficiente");
        }

        Transaction transaction = new Transaction(fromAccount,toAccount,dto.getAmount(),TransactionType.TRANSFER,TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
        accountRepository.save(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));
        accountRepository.save(toAccount);

        return converToDto(transaction);
    }

    public List<TransactionResponseDTO> getTransactionHistory(String accountNumber){
        Account account = accountService.findAccountByNumber(accountNumber);

        User user = getCurrentUser();
        if(!user.getCnp().equals(account.getUser().getCnp()) && !user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de a vizualiza istoricul tranzactiilor acestui cont");
        }

        Long id = account.getId();
        return transactionRepository.findByFromAccountIdOrToAccountIdOrderByTimestampDesc(id,id).stream().map(this::converToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponseDTO> getAllTransactionsForAdmin(){
        return transactionRepository.findAll().stream().map(this::converToDto).collect(Collectors.toList());
    }
}
