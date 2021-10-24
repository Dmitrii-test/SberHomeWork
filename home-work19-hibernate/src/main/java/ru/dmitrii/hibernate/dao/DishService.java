package ru.dmitrii.hibernate.dao;

import org.springframework.stereotype.Component;
import ru.dmitrii.hibernate.model.Dish;
import ru.dmitrii.hibernate.model.Ingredient;
import ru.dmitrii.hibernate.model.Recipe;

import javax.persistence.Transient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Component
public class DishService {

    private final DishDAO dishDAO;

    public DishService(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public Dish findDish(int id) {
        return dishDAO.findByDish(id);
    }

    public Map<String, Object> findDish(String s) {
        return dishDAO.findByDish(s);
    }


    public void saveDish(Dish dish) {
        dishDAO.save(dish);
    }

    public void deleteDish(Dish dish) {
        dishDAO.delete(dish);
    }

    public void updateDish(Dish dish) {
        dishDAO.update(dish);
    }

    public void addDish(String dish) {
        int recipeId = dishDAO.findByRecipe(dish);
        if (recipeId != 0) {
            System.out.printf("Рецепт %s уже есть %n", dish);
            return;
        }
        Recipe byRecipe = new Recipe(dish);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Введите название ингредиента или stop для окончания: ");
                String ing = bufferedReader.readLine();
                if (ing.equalsIgnoreCase("stop")) break;
                Ingredient ingredient = new Ingredient(ing);
                System.out.printf("Введите вес ингредиента %s в гр. : ", ingredient);
                int weight = Integer.parseInt(bufferedReader.readLine());
                saveDish(new Dish(byRecipe, ingredient, weight));
                System.out.println(ingredient + " добавлен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



