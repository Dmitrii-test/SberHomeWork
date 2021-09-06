package ru.dmitrii.homework01_main.convert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static ru.dmitrii.homework01_main.convert.Functions.*;


public class Converter {
    public static void main(String[] args) {
        String tempInit = "";
        String tempResult = "";
        double value = 0;
        double result = 0;

        // Открываем Буфер ридер через try catch resurce
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            //В цикле получаем единицу температуры
            while (true) {
                System.out.println(" Введите единицу температуры \n" +
                        "(Цельсию - C, Кельвину - K, Фаренгейту - F, Выход - E):");
                tempInit = reader.readLine().toUpperCase();
                if (tempInit.equals("E")) {
                    return;
                }
                if (!tempInit.equals("C") && !tempInit.equals("K") && !tempInit.equals("F")) {
                    System.out.println("Не правильно введена единица температуры");
                    continue;
                }
                break;
            }
            //В цикле получаем значение
            while (true) {
                System.out.println("Введите значение:");
                try {
                    value = Double.parseDouble(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Введенно не число");
                    continue;
                }
                break;
            }
            //В цикле получаем конечную единицу температуры
            while (true) {
                System.out.println(" Введите получаемую единицу температуры \n" +
                        "(Цельсию - C, Кельвину - K, Фаренгейту - F):");
                tempResult = reader.readLine().toUpperCase();
                if (!tempResult.equals("C") && !tempResult.equals("K") && !tempResult.equals("F")) {
                    System.out.println("Не правильно введена единица температуры");
                    continue;
                }
                if (tempResult.equals(tempInit)) {
                    System.out.println("Введеная единица температуры совпадает с исходной \n");
                    continue;
                }
                break;
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка ввода " + e);
        }
        // Через свич подбераем метод и получаем результат вычислений
        switch (tempInit) {
            case ("C"): {
                if (tempResult.equals("K")) result=convertCK(value);
                else result=convertCF(value);
                break;
            }
            case ("K"): {
                if (tempResult.equals("C")) result=convertKC(value);
                else result=convertKF(value);
                break;
            }
            case ("F"): {
                if (tempResult.equals("C")) result=convertFC(value);
                else result=convertFK(value);
                break;
            }
        }
        String printOut = String.format(("Результат: %.2f "), result);
        System.out.println(printOut + tempResult);
    }
}

