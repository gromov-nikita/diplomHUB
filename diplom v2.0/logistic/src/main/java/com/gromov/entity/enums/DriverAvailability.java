package com.gromov.entity.enums;

public enum DriverAvailability {
    AVAILABLE("Свободен"),
    NOT_AVAILABLE("Занят"),
    ALL("Все");
    private String name;
    private DriverAvailability(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static DriverAvailability getDriverAvailabilityByName(String name) {
        switch (name) {
            case "Свободен" : {
                return DriverAvailability.AVAILABLE;
            }
            case "Занят" : {
                return  DriverAvailability.NOT_AVAILABLE;
            }
            case "Все" : {
                return  DriverAvailability.ALL;
            }
            default : {
                return null;
            }
        }
    }

}
