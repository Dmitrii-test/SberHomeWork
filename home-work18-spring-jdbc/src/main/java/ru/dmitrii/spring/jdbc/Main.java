package ru.dmitrii.spring.jdbc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.dmitrii.spring.jdbc.dao.DataConfiguration;
import ru.dmitrii.spring.jdbc.dao.DishDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DataConfiguration.class);
        context.refresh();
        DishDAO dishDAO = context.getBean(DishDAO.class);
        String recipe;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите название рецепта: ");
            recipe = reader.readLine();
            List<String> showRecept = dishDAO.showRecept(recipe);
            System.out.printf("Рецепт %s : %n",recipe);
            System.out.println("--------------");
            showRecept.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

