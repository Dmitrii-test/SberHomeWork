package ru.dmitrii.hibernate.dao;


import ru.dmitrii.hibernate.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipesRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByNameLike(String name);

    @Query("select r from Ingredient a left join Recipe r on a.recipes = r where r.name like :nameLike")
    List<Recipe> onlySpecRecipes(@Param("type") String type, @Param("nameLike") String nameLike);
}
