package ru.dmitrii.hibernate.dao;


import ru.dmitrii.hibernate.model.Recipes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipesRepository extends CrudRepository<Recipes, Long> {

    List<Recipes> findByNameLike(String name);

    @Query("select r from Ingredients a left join Recipes r on a.recipes = r where r.name like :nameLike")
    List<Recipes> onlySpecRecipes(@Param("type") String type, @Param("nameLike") String nameLike);
}
