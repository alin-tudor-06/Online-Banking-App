package com.alin.banking.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String cnp;

    @NotBlank
    private String password;

    public LoginRequest(){}


    public LoginRequest(String cnp, String password) {
        this.cnp = cnp;
        this.password = password;
    }

    public String getCnp() {
        return cnp;
    }

    public String getPassword() {
        return password;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
