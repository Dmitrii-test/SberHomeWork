package ru.dmitrii.hibernate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "INGREDIENTS")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "recipe_id")
//    private Recipe recipes;
//    @OneToMany(mappedBy = "ingredients", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    private List<Dish> Dish = new ArrayList<>();

    public Ingredient(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient - " +
                "name= '" + name;
    }
}
