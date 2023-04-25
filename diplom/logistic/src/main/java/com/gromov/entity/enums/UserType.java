package com.gromov.entity.enums;

public enum UserType {
    CUSTOMER("Клиент"),
    MANAGER("Мэнеджер");
    private String name;
    private UserType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
