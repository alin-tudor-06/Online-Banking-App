package com.alin.banking.controller;

import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.model.User;
import com.alin.banking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "List all users (ADMIN only)")
    @GetMapping("/admin/all")
    public List<UserResponseDTO> findAllUsers(){ return userService.findAllUsers();}

    @Operation(summary = "Find user by CNP (own or ADMIN)")
    @GetMapping("/cnp/{cnp}")
    public UserResponseDTO findByCnp(@PathVariable String cnp){
        return userService.findByCnp(cnp);
    }

    @Operation(summary = "Search users by first and last name(ADMIN only")
    @GetMapping("/by-name")
    public List<UserResponseDTO> findByFirstAndLastName(@RequestParam String firstName,@RequestParam String lastName){
        return userService.findByFirstAndLastName(firstName,lastName);
    }

    @Operation(summary = "Update email and/or address (own or ADMIN)")
    @PutMapping("/{cnp}")
    public UserResponseDTO updateUser(@PathVariable String cnp,@RequestBody Map<String,String> request){
        String email = request.get("email");
        String address =  request.get("address");
        return userService.updateUser(cnp,email,address);
    }

    @Operation(summary = "Delete a user (own or ADMIN)")
    @DeleteMapping("/{cnp}")
    public String deleteUser(@PathVariable String cnp){
        userService.deleteUser(cnp);
        return "Utilizatorul a fost sters";
    }
}
