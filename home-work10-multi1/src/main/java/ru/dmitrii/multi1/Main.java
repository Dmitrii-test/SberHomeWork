package ru.dmitrii.multi1;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        List<Integer> integers =
                random.ints(0, 50).limit(20).boxed().collect(Collectors.toList());
        WriteFile(integers);
        List<Integer> list = ReadFile();
        for (Integer number : list) {
            Thread thread = new Thread(() -> {
                BigInteger  result = factorial(number);
                System.out.printf("Факториал числа %d равен %d вычислил %s \n",
                        number, result, Thread.currentThread().getName());
            });
            thread.start();
        }

    }


    /**
     * Метод подсчёта факториала
     * @param number int
     * @return long
     */
    public static BigInteger factorial(int number) {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= number; ++i) ret = ret.multiply(BigInteger.valueOf(i));
        return ret;
    }

    /**
     * Метод записи листа в файл
     * @param integers List<Integer>
     */
    public static void WriteFile(List<Integer> integers) {
        try (FileWriter writer = new FileWriter("Test.txt", StandardCharsets.UTF_8)) {
            for (Integer i : integers) {
                System.out.println("Записываем " + i);
                writer.write(i.toString());
                writer.write(" ");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла " + e.getMessage());
        }
    }

    /**
     * Метод получения из файла листа integer
     * @return List<Integer>
     */
    public static List<Integer> ReadFile() {
        File file = new File("Test.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        List<Integer> list = new ArrayList<>();
        while (scan != null && scan.hasNextInt()) {
            int i = scan.nextInt();
            System.out.println("Полученно " + i);
            list.add(i);
        }
        return list;
    }
}
