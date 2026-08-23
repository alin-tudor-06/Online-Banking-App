package com.alin.banking.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {

    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String ownerFirstName;
    private String ownerLastName;

    public AccountResponseDTO(){}
    public AccountResponseDTO(String accountNumber,BigDecimal balance,String currency,String ownerFirstName,String ownerLastName){
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.currency=currency;
        this.ownerFirstName=ownerFirstName;
        this.ownerLastName=ownerLastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }
}
