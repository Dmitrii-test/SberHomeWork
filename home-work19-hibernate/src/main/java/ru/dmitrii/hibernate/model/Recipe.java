package ru.dmitrii.hibernate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RECIPES")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    @OneToMany(mappedBy = "recipes", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    private List<Ingredient> ingredientsList = new ArrayList<>();
//    @OneToMany(mappedBy = "recipes", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    private List<Dish> Dish = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recipe - " +
                "name=" + name;

    }
}
