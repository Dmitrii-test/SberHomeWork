package ru.dmitrii.multi1;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10,2,12,4,3,15);
        for (Integer number : list) {
            Thread thread = new Thread(() -> {
               long result = factorial(number);
                System.out.printf("Факториал числа %d равен %d вычислил %s \n",
                        number, result, Thread.currentThread().getName());
            });
            thread.start();
        }

    }


    public static long factorial(int number) {
        if (number == 1) return 1L;
        else return number * factorial(number - 1);
    }
}
