package com.gromov.entity.enums;

public enum RequestStatus {
    CANCELED("Отменен"),
    IN_PROCESS("Обрабатывается"),
    ACCEPTED("Принят");
    private String name;
    private RequestStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
