package ru.dmitrii.spring.jdbc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.dmitrii.spring.jdbc.dao.DataConfiguration;
import ru.dmitrii.spring.jdbc.dao.DishDAO;

import java.io.BufferedReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DataConfiguration.class);
        context.refresh();
        DishDAO dishDAO = context.getBean(DishDAO.class);
        BufferedReader bufferedReader = context.getBean(BufferedReader.class);
        String operations;
        try {
            while (true) {
                System.out.println("Введите название операции");
                System.out.println("ADD - добавить рецепт, REMOVE - удалить рецепт, SHOW - показать рецепт," +
                        "EXIT - для выхода :");
                operations = bufferedReader.readLine().toUpperCase();
                switch (operations) {
                    case ("ADD"):
                        System.out.println("Введите название рецепта для добавления: ");
                        String a = bufferedReader.readLine();
                        dishDAO.addDish(a);
                        break;
                    case ("REMOVE"):
                        System.out.println("Введите название рецепта для удаления: ");
                        String s = bufferedReader.readLine();
                        dishDAO.delete(s);
                        break;
                    case ("SHOW"):
                        System.out.println("Введите название рецепта для показа: ");
                        String o = bufferedReader.readLine();
                        dishDAO.showRecept(o);
                        break;
                    case ("EXIT"):
                        return;
                    default:
                        System.out.println("Не правильная операция");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

