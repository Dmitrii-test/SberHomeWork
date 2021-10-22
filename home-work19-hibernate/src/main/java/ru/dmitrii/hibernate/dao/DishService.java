package ru.dmitrii.hibernate.dao;

import org.springframework.stereotype.Component;
import ru.dmitrii.hibernate.model.Dish;

@Component
public class DishService {

    private final DishDAO dishDAO;

    public DishService(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public Dish findDish(int id) {
        return dishDAO.findById(id);
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



}
