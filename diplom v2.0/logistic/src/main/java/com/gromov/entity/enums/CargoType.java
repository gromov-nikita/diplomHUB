package com.gromov.entity.enums;

import java.util.LinkedList;
import java.util.List;

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
    public static List<String> getListOfNames() {
        List<String> list = new LinkedList<>();
        for(CargoType x : CargoType.values()) {
            list.add(x.getName());
        }
        return list;
    }
}
