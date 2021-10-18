package ru.dmitrii.spring.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DishDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer showID(String recipe) {
        recipe = "%" + recipe + "%";
        return jdbcTemplate.queryForObject("SELECT id FROM RECIPES WHERE name LIKE ?", Integer.class, recipe);
    }

    public List<String> showRecept (String recipe) {
        return jdbcTemplate.queryForList("SELECT INGREDIENTS.NAME FROM  Dish LEFT JOIN INGREDIENTS \n" +
                "    ON ingredients_id = id \n" +
                "    WHERE Dish.recipes_id = ? GROUP BY INGREDIENTS.NAME;", String.class, showID(recipe));
    }
}
