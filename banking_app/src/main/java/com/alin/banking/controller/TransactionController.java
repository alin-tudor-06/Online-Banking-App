package com.alin.banking.controller;

import com.alin.banking.dto.TransactionRequestDTO;
import com.alin.banking.dto.TransactionResponseDTO;
import com.alin.banking.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionResponseDTO deposit(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.deposit(dto);
    }

    @PostMapping("/withdraw")
    public TransactionResponseDTO withdraw(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.withdraw(dto);
    }

    @PostMapping("/transfer")
    public  TransactionResponseDTO transfer(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.transfer(dto);
    }

    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponseDTO> transactionHistory(@PathVariable String accountNumber){
        return transactionService.getTransactionHistory(accountNumber);
    }

    @GetMapping("/admin/all")
    public List<TransactionResponseDTO> getAllTransactionsForAdmin(){
        return transactionService.getAllTransactionsForAdmin();
    }
}
