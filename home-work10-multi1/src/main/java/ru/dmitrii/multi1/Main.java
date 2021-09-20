package ru.dmitrii.multi1;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    static volatile List<Integer> integers;

    public static void main(String[] args) {
        Random random = new Random();
        integers = random.ints(0, 50).limit(20).boxed().collect(Collectors.toList());
        for (Integer number : integers) {
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
