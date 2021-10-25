package ru.dmitrii.mvc.model;

public class CalculatorImpl{

    public static long factorial(int number) {
        if (number == 1) return 1L;
        else return number* factorial(number-1);
    }
}
