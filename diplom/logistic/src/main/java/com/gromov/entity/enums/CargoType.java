package com.gromov.entity.enums;

public enum CargoType {
    LIQUID("Жидкий"),
    SOLID("Твердый");
    private String name;
    private CargoType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static CargoType getCargoTypeByName(String name) {
        switch (name) {
            case "Жидкий" : {
                return CargoType.LIQUID;
            }
            case "Твердый" : {
                return  CargoType.SOLID;
            }
            default : {
                return null;
            }
        }
    }
}
