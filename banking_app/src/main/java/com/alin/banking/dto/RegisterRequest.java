package com.alin.banking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
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

    @NotBlank
    @Size(min = 6)
    private String password;

    public RegisterRequest(){}


    public RegisterRequest(String firstName, String lastName, String cnp, String email, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.email = email;
        this.address = address;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
