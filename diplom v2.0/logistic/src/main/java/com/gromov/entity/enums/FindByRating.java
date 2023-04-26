package com.gromov.entity.enums;

public enum FindByRating {
    NOTHING("Нет оценок"),
    ALL("Все"),
    MORE_TWO("Выше двух включительно"),
    MORE_THREE("Выше трех включительно"),
    MORE_FOUR("Выше четырех включительно"),
    LESS_TWO("Ниже двух"),
    LESS_THREE("Ниже трех"),
    LESS_FOUR("Ниже четырех");
    private String name;
    private FindByRating(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FindByRating getRatingByName(String name) {
        switch (name) {
            case "Все" : {
                return FindByRating.ALL;
            }
            case "Выше двух включительно" : {
                return FindByRating.MORE_TWO;
            }
            case "Выше трех включительно" : {
                return FindByRating.MORE_THREE;
            }
            case "Выше четырех включительно" : {
                return FindByRating.MORE_FOUR;
            }
            case "Ниже двух" : {
                return FindByRating.LESS_TWO;
            }
            case "Ниже трех" : {
                return FindByRating.MORE_THREE;
            }
            case "Ниже четырех" : {
                return FindByRating.LESS_FOUR;
            }
            case "Нет оценок" : {
                return FindByRating.NOTHING;
            }
            default : {
                return null;
            }
        }
    }
}
