package com.example.demo.util;

public enum RoleConstraint {
    ROLE_ADMIN("ROLE_ADMIN", "Admin role"),
    ROLE_CUSTOMER("ROLE_CUSTOMER", "Customer role");
    private String name;
    private String description;

    RoleConstraint(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
