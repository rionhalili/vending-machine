package com.example.vendingmachine.user.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String username;
    private String password;
    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if(this.username.isEmpty()) {
            errors.add("Username cannot be empty");
        }
        if(this.password.isEmpty()) {
            errors.add("Password cannot be empty");
        }
        return errors;
    }
}
