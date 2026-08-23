package com.alin.banking.dto;

import jakarta.validation.constraints.*;

public class UserCreateDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{13}")
    private String cnp;

    @Email
    private String email;

    private String address;

    public UserCreateDTO(){}

    public UserCreateDTO(String firstName,String lastName,String cnp,String email,String address){
        this.firstName=firstName;
        this.lastName=lastName;
        this.cnp=cnp;
        this.email=email;
        this.address=address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
