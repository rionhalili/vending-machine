package com.example.vendingmachine.user.dto;

import com.example.vendingmachine.role.model.RoleType;

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
