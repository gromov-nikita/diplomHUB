package com.gromov.entity.enums;

public enum Rating {
    NOTHING(0,"Нет отзыва"),
    ONE(1,"Один"),
    TWO(2,"Два"),
    THREE(3,"Три"),
    FOUR(4,"Четыре"),
    FIVE(5,"Пять");
    private int mark;
    private String name;
    private Rating(int mark,String name) {
        this.mark = mark;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMark() {
        return mark;
    }
    public Rating getRatingByMark(int mark) {
        switch (mark) {
            case 1 : {
                return Rating.ONE;
            }
            case 2 : {
                return Rating.TWO;
            }
            case 3 : {
                return Rating.THREE;
            }
            case 4 : {
                return Rating.FOUR;
            }
            case 5 : {
                return Rating.FIVE;
            }
            case 0 : {
                return Rating.NOTHING;
            }
            default : {
                return null;
            }
        }
    }
}
