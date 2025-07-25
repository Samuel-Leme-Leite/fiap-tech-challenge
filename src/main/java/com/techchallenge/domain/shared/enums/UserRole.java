package com.techchallenge.domain.shared.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Administrador"),
    CUSTOMER("Cliente"),
    EMPLOYEE("Funcionário");

    private final String description;
    UserRole(String description) {
        this.description = description;
    }
}
