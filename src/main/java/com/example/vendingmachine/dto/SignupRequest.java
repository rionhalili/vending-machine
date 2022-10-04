package com.example.vendingmachine.dto;

import com.example.vendingmachine.model.RoleType;

import java.util.Set;

public class SignupRequest {

    private String username;

    private RoleType role;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return this.role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
