package com.alin.banking.controller;

import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.model.User;
import com.alin.banking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserCreateDTO dto){
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserResponseDTO> findAllUsers(){ return userService.findAllUsers();}

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping("/cnp/{cnp}")
    public UserResponseDTO findByCnp(@PathVariable String cnp){
        return userService.findByCnp(cnp);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id,@RequestBody Map<String,String> request){
        String email = request.get("email");
        String address =  request.get("address");
        return userService.updateUser(id,email,address);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "Utilizatorul a fost sters";
    }
}
