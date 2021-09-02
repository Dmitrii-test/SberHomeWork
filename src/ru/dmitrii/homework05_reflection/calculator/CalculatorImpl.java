package ru.dmitrii.homework05_reflection.calculator;

public class CalculatorImpl implements Calculator{

    @Override
    public long factorial(int number) {
        if (number == 1) return 1;
        else return number* factorial(number-1);
    }

    @Override
    public long fibonacci(int num) {
        long a = 0;
        long b = 1;
        for (int i = 2; i <= num; ++i) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }
}
