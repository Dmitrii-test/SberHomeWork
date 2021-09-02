package ru.dmitrii.homework05_reflection.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final String[] operations = {"факториал", "фибоначчи"};
    public static void main(String[] args) {
        int num = 0;
        Calculator calculator = (Calculator) new MetricHandler(new CalculatorImpl()).newInstance();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Выберите операцию вычисления: ");
                System.out.println("1-Факториал, 2-Фибоначчи, 3-Выход ");
                int oper = Integer.parseInt(reader.readLine());
                if (oper<1 || oper>3) {
                    System.out.println("Не правильный выбор");
                    continue;
                }
                else if (oper==3) break;
                System.out.printf("Введите число для которого посчитать %s:\n", operations[oper-1]);
                num = Integer.parseInt(reader.readLine());
                long rez;
                if (oper==1) rez = calculator.factorial(num);
                else rez= calculator.fibonacci(num);
                System.out.printf("%s числа %d равен %d \n",operations[oper-1],  num, rez);
            }
        } catch (NumberFormatException e) {
            System.out.println("Не правильное число!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
