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
@Tag(name = "Tranzactii",description = "Operatiuni pentru tranzactii bancare(depunere,retragere,transfer,istoric)")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Depune bani intr-un cont(doar proprietar sau ADMIN)")
    @PostMapping("/deposit")
    public TransactionResponseDTO deposit(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.deposit(dto);
    }

    @Operation(summary = "Retrage bani dintr-un cont(doar proprietar sau ADMIN)")
    @PostMapping("/withdraw")
    public TransactionResponseDTO withdraw(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.withdraw(dto);
    }

    @Operation(summary = "Transfera bani intre doua conturi(doar proprietarul contului sursa sau ADMIN)")
    @PostMapping("/transfer")
    public  TransactionResponseDTO transfer(@Valid @RequestBody TransactionRequestDTO dto){
        return transactionService.transfer(dto);
    }

    @Operation(summary = "Returneaza istoricul tranzactiilor pentru un cont(doar proprietar sau ADMIN)")
    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponseDTO> transactionHistory(@PathVariable String accountNumber){
        return transactionService.getTransactionHistory(accountNumber);
    }

    @Operation(summary = "Listeaza toate tranzactiile din sistem(doar ADMIN)")
    @GetMapping("/admin/all")
    public List<TransactionResponseDTO> getAllTransactionsForAdmin(){
        return transactionService.getAllTransactionsForAdmin();
    }
}
