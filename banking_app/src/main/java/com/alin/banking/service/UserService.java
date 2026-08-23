package com.alin.banking.service;

import com.alin.banking.dto.UserCreateDTO;
import com.alin.banking.dto.UserResponseDTO;
import com.alin.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private User convertToEntity(UserCreateDTO dto){
        return new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCnp(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    public User findUserByCnp(String cnp) {
        return userRepository.findByCnp(cnp)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
    }

    public User createUserEntity(UserCreateDTO dto) {
        User user = new User(dto.getFirstName(), dto.getLastName(), dto.getCnp(), dto.getEmail(), dto.getAddress());
        return userRepository.save(user);
    }

    public UserResponseDTO createUser(UserCreateDTO dto){
        User new_user = convertToEntity(dto);
        User saved = userRepository.save(new_user);
        return convertToDto(saved);
    }

    public UserResponseDTO findByCnp(String cnp){
        User user = userRepository.findByCnp(cnp).orElseThrow(() -> new RuntimeException("Utilizatorul cu CNP-ul " + cnp + " nu a fost gasit"));
        return convertToDto(user);
    }

    public UserResponseDTO findById(Long id){
        User user =  userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost gasit"));
        return convertToDto(user);
    }

    public List<UserResponseDTO> findByFirstAndLastName(String firstName,String lastName){
        return userRepository.findByFirstNameAndLastName(firstName,lastName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<UserResponseDTO> findAllUsers(){
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long id,String new_email,String new_address){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizatorul cu ID-ul " + id + " nu exista"));
        if(new_email != null) user.setEmail(new_email);
        if(new_address != null) user.setAddress(new_address);
        userRepository.save(user);
        return convertToDto(user);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizatorul cu ID-ul " + id + " nu exista"));
        userRepository.delete(user);
    }
}
