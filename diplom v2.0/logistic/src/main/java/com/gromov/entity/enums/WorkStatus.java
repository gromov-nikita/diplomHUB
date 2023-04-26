package com.gromov.entity.enums;

public enum WorkStatus {
    FREE("Свободен"),
    BOOKED("Занят");
    private String name;
    private WorkStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
