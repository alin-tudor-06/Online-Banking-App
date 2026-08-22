package com.alin.banking.repository;

import com.alin.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByCnp(String cnp);
}
