package com.alin.banking.service;

import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.model.Role;
import com.alin.banking.repository.UserRepository;
import com.alin.banking.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.alin.banking.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserResponseDTO convertToDto(User user){
        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress()
        );
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

    public User findUserByCnp(String cnp) {
        return userRepository.findByCnp(cnp)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
    }

    public UserResponseDTO findByCnp(String cnp){
        User user = userRepository.findByCnp(cnp).orElseThrow(() -> new RuntimeException("Utilizatorul cu CNP-ul " + cnp + " nu a fost gasit"));

        User currentUser = getCurrentUser();
        if(!currentUser.getCnp().equals(user.getCnp())){
            if(!currentUser.getRole().equals(Role.ADMIN)) {
                throw new RuntimeException("Nu aveti permisiunea de a vizualiza acest cont");
            }
        }
        return convertToDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> findByFirstAndLastName(String firstName,String lastName){
        return userRepository.findByFirstNameAndLastName(firstName,lastName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> findAllUsers(){
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(String cnp,String new_email,String new_address){
        User user = userRepository.findByCnp(cnp).orElseThrow(() -> new RuntimeException("Utilizatorul cu CNP-ul " + cnp + " nu exista"));

        User currentUser = getCurrentUser();
        if(!currentUser.getCnp().equals(user.getCnp()) && !currentUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de a modifica detaliile acestui cont");
        }

        if(new_email != null) user.setEmail(new_email);
        if(new_address != null) user.setAddress(new_address);
        userRepository.save(user);
        return convertToDto(user);
    }

    public void deleteUser(String cnp){
        User user = userRepository.findByCnp(cnp).orElseThrow(() -> new RuntimeException("Utilizatorul cu CNP-ul " + cnp + " nu exista"));

        User currentUser = getCurrentUser();
        if(!currentUser.getCnp().equals(user.getCnp()) && !currentUser.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Nu aveti permisiunea de sterge acest cont");
        }

        userRepository.delete(user);
    }
}
