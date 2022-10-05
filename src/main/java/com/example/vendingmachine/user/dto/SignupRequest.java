package com.example.vendingmachine.user.dto;

import com.example.vendingmachine.role.model.RoleType;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if(this.username.isEmpty()) {
            errors.add("Username cannot be empty");
        }
        if(this.password.isEmpty()) {
            errors.add("Password cannot be empty");
        }
        if(this.role.getName().isEmpty()) {
            errors.add("Role cannot be empty");
        }

        return errors;
    }
}
