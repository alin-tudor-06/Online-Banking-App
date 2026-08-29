package com.alin.banking.controller;

import com.alin.banking.dto.TransactionRequestDTO;
import com.alin.banking.dto.TransactionResponseDTO;
import com.alin.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Banking transaction operations (deposit, withdraw, transfer, history)")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Deposit money into an account (owner or ADMIN)")
    @PostMapping("/deposit")
    public TransactionResponseDTO deposit(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.deposit(dto);
    }

    @Operation(summary = "Withdraw money from an account (owner or ADMIN)")
    @PostMapping("/withdraw")
    public TransactionResponseDTO withdraw(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.withdraw(dto);
    }

    @Operation(summary = "Transfer money between accounts (source account owner or ADMIN)")
    @PostMapping("/transfer")
    public  TransactionResponseDTO transfer(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.transfer(dto);
    }

    @Operation(summary = "Get transaction history for an account (owner or ADMIN)")
    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponseDTO> transactionHistory(@PathVariable String accountNumber){
        return transactionService.getTransactionHistory(accountNumber);
    }

    @Operation(summary = "List all transactions in the system (ADMIN only)")
    @GetMapping("/admin/all")
    public List<TransactionResponseDTO> getAllTransactionsForAdmin(){
        return transactionService.getAllTransactionsForAdmin();
    }
}
