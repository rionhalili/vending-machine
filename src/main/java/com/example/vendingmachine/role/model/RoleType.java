package com.example.vendingmachine.role.model;

public enum RoleType {
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_BUYER("ROLE_BUYER"),
    ROLE_OMNIA("ROLE_OMNIA");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getName() {
        return role;
    }
}
