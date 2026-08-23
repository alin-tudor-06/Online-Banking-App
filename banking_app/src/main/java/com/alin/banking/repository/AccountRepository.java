package com.alin.banking.repository;

import com.alin.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>{
    Optional<Account> findByAccountNumber(String accountnumber);
}
