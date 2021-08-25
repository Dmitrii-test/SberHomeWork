package ru.dmitrii.homework01_main.convert;

public final class Functions {

    private Functions() {
    }

    /**
     * Метод перевода из градусов Цельсия в Кельвины
     */
    static double convertCK(double value) {
        return value + 273.15;
    }

    /**
     * Метод перевода из градусов Цельсия в градусы Фаренгейта
     */
    static double convertCF(double value) {
        return (9*value/5)+32;
    }

    /**
     * Метод перевода из Кельвинов в градусы Цельсия
     */
    static double convertKC(double value) {
        return value - 273.15;
    }

    /**
     * Метод перевода Кельвины в градусы Фаренгейта
     */
    static double convertKF(double value) {
        return (value-273.15)*9/5+32;
    }

    /**
     * Метод перевода из градусов Фаренгейта в градусы Цельсия
     */
    static double convertFC(double value) {
        return (value-32)*5/9;
    }

    /**
     * Метод перевода из градусов Фаренгейта в Кельвины
     */
    static double convertFK(double value) {
        return  (value-32)*5/9+273.1;
    }

}
