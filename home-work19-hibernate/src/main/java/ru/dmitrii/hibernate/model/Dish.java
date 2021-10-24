package ru.dmitrii.hibernate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Dish")
@Getter
@Setter
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "recipes_id", nullable = false)
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "ingredients_id", nullable = false)
    private Ingredient ingredient;

    private int weight;

    public Dish() {
    }

    public Dish(Recipe recipe) {
        this.recipe = recipe;
    }

    public Dish(Recipe recipe, Ingredient ingredient, int weight) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Dish {"
                + recipe +
                ingredient +
                " weight= " + weight +
                '}';
    }
}
