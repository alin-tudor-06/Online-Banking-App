package com.alin.banking.dto;

public class JwtResponse {
    private String token;
    private String cnp;
    private String role;

    public JwtResponse(){}


    public JwtResponse(String token, String cnp, String role) {
        this.token = token;
        this.cnp = cnp;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getCnp() {
        return cnp;
    }

    public String getRole() {
        return role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
