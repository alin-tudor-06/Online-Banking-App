package com.alin.banking.controller;

import com.alin.banking.model.User;
import com.alin.banking.service.UserService;
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
    public User createUser(@RequestBody Map<String,String> request){
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String cnp = request.get("cnp");
        String email = request.get("email");
        String address = request.get("address");
        return userService.createUser(firstName,lastName,cnp,email,address);
    }

    @GetMapping
    public List<User> findAllUsers(){ return userService.findAllUsers();}

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping("/cnp/{cnp}")
    public User findByCnp(@PathVariable String cnp){
        return userService.findByCnp(cnp);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody Map<String,String> request){
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
