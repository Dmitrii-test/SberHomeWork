package ru.dmitrii.jdbc;

import ru.dmitrii.jdbc.calc.Calculator;
import ru.dmitrii.jdbc.calc.CalculatorImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = (Calculator) new MetricHandler(new CalculatorImpl()).newInstance();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Введите число для которого посчитать Фибоначчи, ");
                System.out.println("или введите 0 для выхода: ");
                int num = Integer.parseInt(reader.readLine());
                if (num==0) break;
                long rez;
                rez= calculator.fibonachi(num);
                System.out.printf("Фибоначчи числа %d равен %d \n", num, rez);
            }
        } catch (NumberFormatException e) {
            System.out.println("Не правильное число!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
