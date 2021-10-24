package ru.dmitrii.hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.dmitrii.hibernate.dao.DishService;
import ru.dmitrii.hibernate.model.Dish;
import ru.dmitrii.hibernate.model.Ingredient;
import ru.dmitrii.hibernate.model.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.dmitrii.hibernate");
        context.refresh();
        DishService dishService = context.getBean(DishService.class);
        String operations;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Введите название операции");
                System.out.println("ADD - добавить рецепт, REMOVE - удалить рецепт, SHOW - показать рецепт," +
                        "EXIT - для выхода :");
                operations = bufferedReader.readLine().toUpperCase();
                switch (operations) {
                    case ("ADD"):
                        System.out.println("Введите название рецепта для добавления: ");
                        String recipe = bufferedReader.readLine();
                        dishService.addDish(recipe);
                        break;
                    case ("REMOVE"):
                        System.out.println("Введите название рецепта для удаления: ");
                        String s = bufferedReader.readLine();
                        dishService.deleteDish(new Dish(new Recipe(s)));
                        break;
                    case ("SHOW"):
                        System.out.println("Введите название рецепта для показа: ");
                        String o = bufferedReader.readLine();
                        Map<String, Object> dish = dishService.findDish(o);
                        dish.forEach((k,v) -> System.out.println(k + " - " + v));
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

