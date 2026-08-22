package com.alin.banking.service;

import com.alin.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alin.banking.model.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String firstName,String lastName,String cnp,String email,String address){
        User new_user = new User(firstName,lastName,cnp,email,address);
        userRepository.save(new_user);
        return new_user;
    }

    public User findByCnp(String cnp){
        return userRepository.findByCnp(cnp).orElseThrow(() -> new RuntimeException("Utilizatorul cu CNP-ul " + cnp + " nu a fost gasit"));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost gasit"));
    }
}
