package ru.dmitrii.hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.dmitrii.hibernate.dao.DishService;
import ru.dmitrii.hibernate.model.Dish;
import ru.dmitrii.hibernate.model.Ingredient;
import ru.dmitrii.hibernate.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.dmitrii.hibernate");
        context.refresh();
        DishService dishService = context.getBean(DishService.class);
        Recipe recipe = new Recipe("Борщ");
        Ingredient ingredient = new Ingredient("Картошка");
        dishService.saveDish(new Dish(recipe, ingredient, 500));
        System.out.println(dishService.findDish(1));

        String operations;
//        try {
//            while (true) {
//                System.out.println("Введите название операции");
//                System.out.println("ADD - добавить рецепт, REMOVE - удалить рецепт, SHOW - показать рецепт," +
//                        "EXIT - для выхода :");
//                operations = bufferedReader.readLine().toUpperCase();
//                switch (operations) {
//                    case ("ADD"):
//                        System.out.println("Введите название рецепта для добавления: ");
//                        String a = bufferedReader.readLine();
//                        recipesDAO.addDish(a);
//                        break;
//                    case ("REMOVE"):
//                        System.out.println("Введите название рецепта для удаления: ");
//                        String s = bufferedReader.readLine();
//                        dishDAO.delete(s);
//                        break;
//                    case ("SHOW"):
//                        System.out.println("Введите название рецепта для показа: ");
//                        String o = bufferedReader.readLine();
//                        dishDAO.showRecept(o);
//                        break;
//                    case ("EXIT"):
//                        return;
//                    default:
//                        System.out.println("Не правильная операция");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

