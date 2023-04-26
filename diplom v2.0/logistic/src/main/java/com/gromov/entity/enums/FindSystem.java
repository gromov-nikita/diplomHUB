package com.gromov.entity.enums;

public enum FindSystem {
    NAME("Ф.И.О."),
    CUSTOMER_NAME("Ф.И.О. заказчика"),
    DRIVER_NAME("Ф.И.О. водителя"),
    CUSTOMER_EMAIL("Email заказчика"),
    LESS_PRICE("Меньше цены"),
    MORE_MAX_WEIGHT("Больше грузоподъемности(кг)"),
    MAX_WEIGHT("Грузоподъемность(кг)"),
    CARGO_WEIGHT("Масса груза(кг)"),
    CARGO_TYPE("Тип груза"),
    DRIVER_STATUS("Статус водителя"),
    UNDERLOAD_TRUE("С недогрузом"),
    UNDERLOAD_FALSE("Без недогруза"),
    ALL("Все");
    private String name;
    private FindSystem(String name) {
        this.name = name;
    }
    public static FindSystem getFindSystemTypeByName(String name) {
        switch (name) {
            case "Все" : {
                return  FindSystem.ALL;
            }
            case "Ф.И.О." : {
                return FindSystem.NAME;
            }
            case "Меньше цены" : {
                return  FindSystem.LESS_PRICE;
            }
            case "Больше грузоподъемности(кг)" : {
                return  FindSystem.MORE_MAX_WEIGHT;
            }
            case "Грузоподъемность(кг)" : {
                return  FindSystem.MAX_WEIGHT;
            }
            case "Масса груза(кг)" : {
                return  FindSystem.CARGO_WEIGHT;
            }
            case "Тип груза" : {
                return  FindSystem.CARGO_TYPE;
            }
            case "С недогрузом" : {
                return  FindSystem.UNDERLOAD_TRUE;
            }
            case "Ф.И.О. заказчика" : {
                return  FindSystem.CUSTOMER_NAME;
            }
            case "Ф.И.О. водителя" : {
                return  FindSystem.DRIVER_NAME;
            }
            case "Без недогруза" : {
                return  FindSystem.UNDERLOAD_FALSE;
            }
            case "Email заказчика" : {
                return  FindSystem.CUSTOMER_EMAIL;
            }
            default : {
                return null;
            }
        }
    }
    public String getName() {
        return name;
    }
}
