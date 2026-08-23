package com.alin.banking.dto;

public class UserResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    public UserResponseDTO(){}

    public UserResponseDTO(String firstName,String lastName,String email,String address){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.address=address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
