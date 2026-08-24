package com.alin.banking.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransactionRequestDTO {
    private String fromAccountNumber;
    private String toAccountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String type;

    public TransactionRequestDTO(){}

    public TransactionRequestDTO(String fromAccountNumber,String toAccountNumber,BigDecimal amount,String type){
        this.fromAccountNumber=fromAccountNumber;
        this.toAccountNumber=toAccountNumber;
        this.amount=amount;
        this.type=type;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }
}
