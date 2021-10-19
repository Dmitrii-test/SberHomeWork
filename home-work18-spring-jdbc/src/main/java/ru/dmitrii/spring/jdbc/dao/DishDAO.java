package ru.dmitrii.spring.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DishDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertIngredient;
    private final SimpleJdbcInsert simpleJdbcInsertRecipe;
    private final BufferedReader bufferedReader;

    @Autowired
    public DishDAO(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsertIngredient,
                   SimpleJdbcInsert simpleJdbcInsertRecipe, BufferedReader bufferedReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsertIngredient = simpleJdbcInsertIngredient;
        this.simpleJdbcInsertRecipe = simpleJdbcInsertRecipe;
        this.bufferedReader = bufferedReader;
    }

    /**
     * Получить номер рецепта
     *
     * @param recipe String
     * @return List<Integer>
     */
    @Transactional(readOnly = true)
    public Integer getIDRecipe(String recipe) {
        List<Integer> integers = jdbcTemplate.queryForList("SELECT id FROM RECIPES" +
                " WHERE lower(name) = lower(?)", Integer.class, recipe);
        if (integers.isEmpty()) {
            return 0;
        } else {
            return integers.get(0);
        }
    }

    /**
     * Получить номер рецепта
     *
     * @param ingredient String
     * @return List<Integer>
     */
    @Transactional(readOnly = true)
    public Integer getIDIngredient(String ingredient) {
        List<Integer> integers = jdbcTemplate.queryForList("SELECT id FROM INGREDIENTS" +
                " WHERE lower(name) = lower(?)", Integer.class, ingredient);
        if (integers.isEmpty()) {
            return 0;
        } else {
            return integers.get(0);
        }
    }

    /**
     * Получить номера всех совпадающих рецептов
     *
     * @param recipe String
     * @return List<Integer>
     */
    @Transactional(readOnly = true)
    public List<Integer> getIDs(String recipe) {
        recipe = "%" + recipe + "%";
        return jdbcTemplate.queryForList("SELECT id FROM RECIPES" +
                " WHERE lower(name) LIKE lower(?)", Integer.class, recipe);
    }

    /**
     * Получить имя рецепта по id
     *
     * @param in Integer
     * @return String
     */
    @Transactional(readOnly = true)
    public String getName(Integer in) {
        return jdbcTemplate.queryForObject("SELECT name FROM RECIPES WHERE ID=?", String.class, in);
    }

    /**
     * Выводит рецепт по названию или части названия
     *
     * @param recipe String
     */
    @Transactional
    public void showRecept(String recipe) {
        List<Integer> showID = getIDs(recipe);
        showID.forEach(n -> {
            System.out.printf("Рецепт %s : %n", getName(n));
            System.out.println("---------------");
            System.out.println(getIngredientsMap(n));
        });
    }

    /**
     * Получить список ингредиентов и вес
     *
     * @param n Integer
     * @return Map<String, String>
     */
    private Map<String, String> getIngredientsMap(Integer n) {
        return jdbcTemplate.query("SELECT INGREDIENTS.NAME, Dish.weight FROM  Dish LEFT JOIN INGREDIENTS \n" +
                "    ON ingredients_id = id WHERE Dish.recipes_id = ?", (ResultSetExtractor<Map<String, String>>) resultSet -> {
            HashMap<String, String> mapRet = new HashMap<>();
            while (resultSet.next()) {
                mapRet.put(resultSet.getString("INGREDIENTS.NAME"),
                        resultSet.getString("Dish.weight"));
            }
            return mapRet;
        }, n);
    }

    /**
     * Удалить рецепт
     *
     * @param recipe String
     */
    @Transactional
    public void delete(String recipe) {
        Integer id = getIDRecipe(recipe);
        if (id == 0) System.out.printf("Рецепт %s не найден %n", recipe);
        else jdbcTemplate.update("DELETE FROM RECIPES WHERE id =?", id);
        System.out.println(recipe + " удалён");
    }

    /**
     * Добавить рецепт
     *
     * @param recipe String
     */
    @Transactional
    public void addDish(String recipe) {
        Integer id = getIDRecipe(recipe);
        if (id != 0) {
            System.out.printf("Рецепт %s уже есть %n", recipe);
            return;
        }
        int rec = addRecipe(recipe);
        while (true) {
            try {
                System.out.println("Введите название ингредиента или stop для окончания: ");
                String ingr = bufferedReader.readLine();
                if (ingr.equalsIgnoreCase("stop")) break;
                int ingredient = addIngredient(ingr);
                System.out.printf("Введите вес ингредиента %s в гр. : ", ingr);
                String weight = bufferedReader.readLine();
                jdbcTemplate.update("INSERT INTO DISH(recipes_id, ingredients_id, weight) VALUES(?,?, ?)", rec, ingredient, weight);
                System.out.println(ingr + " добавлен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Добавить рецепт
     *
     * @param recipe String
     */
    @Transactional
    public int addRecipe(String recipe) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", recipe);
        Number id = simpleJdbcInsertRecipe.executeAndReturnKey(params);
        return id.intValue();
    }

    /**
     * Добавить ингредиенты
     *
     * @param ingredient String
     */
    @Transactional
    public int addIngredient(String ingredient) {
        Integer idIngredient = getIDIngredient(ingredient);
        if (idIngredient != 0 ) return idIngredient;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", ingredient);
        Number id = simpleJdbcInsertIngredient.executeAndReturnKey(params);
        return id.intValue();
    }


}
