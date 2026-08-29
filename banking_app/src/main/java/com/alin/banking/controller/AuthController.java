package com.alin.banking.controller;

import com.alin.banking.dto.JwtResponse;
import com.alin.banking.dto.LoginRequest;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.dto.RegisterRequest;
import com.alin.banking.model.Role;
import com.alin.banking.model.User;
import com.alin.banking.repository.UserRepository;
import com.alin.banking.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration and authentication")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody RegisterRequest request){

        if(userRepository.findByCnp(request.getCnp()).isPresent()){
            throw new RuntimeException("CNP-ul a fost deja inregistrat");
        }

        User user = new User(request.getFirstName(), request.getLastName(),request.getCnp(), request.getEmail(),request.getAddress(),passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(user);

        return new UserResponseDTO(user.getFirstName(),user.getLastName(),user.getEmail(),user.getAddress());
    }

    @Operation(summary = "Authenticate user and return JWT token")
    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCnp(),request.getPassword()));

            String token = jwtUtils.generateToken(authentication);

            String role = authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).map(auth -> auth.replace("ROLE_","")).orElse("USER");

            String cnp = authentication.getName();
            return new JwtResponse(token,cnp,role);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"CNP sau parola incorecte");
        }
    }
}
