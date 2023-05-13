package com.gromov.entity.enums;

public enum OrderAgreement {
    CURRENT_DAY("$JAVA(CURRENT_DAY)$"),
    CURRENT_MONTH("$JAVA(CURRENT_MONTH)$"),
    CURRENT_YEAR("$JAVA(CURRENT_YEAR)$"),
    DRIVER_NAME("$JAVA(DRIVER_NAME)$"),
    CUSTOMER_NAME("$JAVA(CUSTOMER_NAME)$"),
    DRIVER_PRICE("$JAVA(DRIVER_PRICE)$"),
    SENDING_DATE("$JAVA(SENDING_DATE)$"),
    DELIVERY_DATE("$JAVA(DELIVERY_DATE)$"),
    FROM("$JAVA(FROM)$"),
    TO("$JAVA(TO)$"),
    MANAGER_NAME("$JAVA(MANAGER_NAME)$");
    private String name;
    private OrderAgreement(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public static OrderAgreement getOrerAgreemenByName(String name) {
        switch (name) {
            case "$JAVA(CURRENT_DAY)$" : {
                return CURRENT_DAY;
            }
            case "$JAVA(CURRENT_MONTH)$" : {
                return CURRENT_MONTH;
            }
            case "$JAVA(CURRENT_YEAR)$" : {
                return CURRENT_YEAR;
            }
            case "$JAVA(DRIVER_NAME)$" : {
                return DRIVER_NAME;
            }
            case "$JAVA(CUSTOMER_NAME)$" : {
                return CUSTOMER_NAME;
            }
            case "$JAVA(DRIVER_PRICE)$" : {
                return DRIVER_PRICE;
            }
            case "$JAVA(SENDING_DATE)$" : {
                return SENDING_DATE;
            }
            case "$JAVA(DELIVERY_DATE)$" : {
                return DELIVERY_DATE;
            }
            case "$JAVA(FROM)$" : {
                return FROM;
            }
            case "$JAVA(TO)$" : {
                return TO;
            }
            case "$JAVA(MANAGER_NAME)$" : {
                return MANAGER_NAME;
            }
            default : {
                return null;
            }
        }
    }
}
