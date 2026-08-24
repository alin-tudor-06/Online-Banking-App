package com.alin.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String fromOwnerName;
    private String toOwnerName;
    private BigDecimal amount;
    private String type;
    private String status;
    private LocalDateTime timestamp;

    public TransactionResponseDTO(){}

    public TransactionResponseDTO(String fromAccountNumber,String toAccountNumber,String fromOwnerName,String toOwnerName,BigDecimal amount,String type,String status,LocalDateTime timestamp){
        this.fromAccountNumber=fromAccountNumber;
        this.toAccountNumber=toAccountNumber;
        this.fromOwnerName=fromOwnerName;
        this.toOwnerName=toOwnerName;
        this.amount=amount;
        this.type=type;
        this.status=status;
        this.timestamp=timestamp;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public String getToAcountNumber() {
        return toAccountNumber;
    }

    public String getFromOwnerName() {
        return fromOwnerName;
    }

    public String getToOwnerName() {
        return toOwnerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public void setToAcountNumber(String toAcountNumber) {
        this.toAccountNumber = toAcountNumber;
    }

    public void setFromOwnerName(String fromOwnerName) {
        this.fromOwnerName = fromOwnerName;
    }

    public void setToOwnerName(String toOwnerName) {
        this.toOwnerName = toOwnerName;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
