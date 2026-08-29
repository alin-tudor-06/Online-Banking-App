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
@Tag(name = "Utilizatori",description = "Operatiuni pentru gestionarea utilizatorilor")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Listeaza toti utilizatorii(doar ADMIN)")
    @GetMapping("/admin/all")
    public List<UserResponseDTO> findAllUsers(){ return userService.findAllUsers();}

    @Operation(summary = "Caută un utilizator după CNP (propriu sau ADMIN)")
    @GetMapping("/cnp/{cnp}")
    public UserResponseDTO findByCnp(@PathVariable String cnp){
        return userService.findByCnp(cnp);
    }

    @Operation(summary = "Caută utilizatori după nume și prenume(doar ADMIN)")
    @GetMapping("/by-name")
    public List<UserResponseDTO> findByFirstAndLastName(@RequestParam String firstName,@RequestParam String lastName){
        return userService.findByFirstAndLastName(firstName,lastName);
    }

    @Operation(summary = "Actualizează email-ul și/sau adresa unui utilizator (propriu sau ADMIN)")
    @PutMapping("/{cnp}")
    public UserResponseDTO updateUser(@PathVariable String cnp,@RequestBody Map<String,String> request){
        String email = request.get("email");
        String address =  request.get("address");
        return userService.updateUser(cnp,email,address);
    }

    @Operation(summary = "Șterge un utilizator (propriu sau ADMIN)")
    @DeleteMapping("/{cnp}")
    public String deleteUser(@PathVariable String cnp){
        userService.deleteUser(cnp);
        return "Utilizatorul a fost sters";
    }
}
