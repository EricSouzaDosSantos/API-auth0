package com.senai.anchieta.api.auth.model;

public enum UserRoles {
    ADMIN("admin"),
    AQV("aqv"),
    COORDENADOR("coordenador"),
    USER("user");

    private String role;

    UserRoles(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
