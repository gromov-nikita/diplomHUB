package com.gromov.entity.enums;

public enum OrderStatus {
    CANCELED("Отменен"),
    PROCESSED("Подготавливается"),
    SENT("Отправлен"),
    DELIVERED("Доставлен"),
    COMPLETED("Выполнен"),
    ALL("Все");

    private String name;
    private OrderStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static OrderStatus getOrderStatusByName(String name) {
        switch (name) {
            case "Отменен": {
                return OrderStatus.CANCELED;
            }
            case "Подготавливается": {
                return OrderStatus.PROCESSED;
            }
            case "Отправлен": {
                return OrderStatus.SENT;
            }
            case "Доставлен": {
                return OrderStatus.DELIVERED;
            }
            case "Выполнен": {
                return OrderStatus.COMPLETED;
            }
            case "Все": {
                return OrderStatus.ALL;
            }
            default: {
                return null;
            }
        }
    }
}
