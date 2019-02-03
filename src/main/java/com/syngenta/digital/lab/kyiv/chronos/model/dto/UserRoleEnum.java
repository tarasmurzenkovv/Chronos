package com.syngenta.digital.lab.kyiv.chronos.model.dto;

public enum UserRoleEnum {
    ADMIN("Admin"), REGULAR("Regular");

    private final String roleAsString;

    UserRoleEnum(String roleAsString) {
        this.roleAsString = roleAsString;
    }

    public String getRoleAsString() {
        return roleAsString;
    }
}
