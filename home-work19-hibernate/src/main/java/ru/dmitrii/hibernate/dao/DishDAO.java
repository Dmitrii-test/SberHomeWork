package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.dmitrii.hibernate.model.Dish;
import ru.dmitrii.hibernate.model.Ingredient;
import ru.dmitrii.hibernate.model.Recipe;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishDAO {

//    final SessionFactory sessionFactory;

    private EntityManager entityManager;

    public DishDAO() {
        EntityManagerFactory myUnit = Persistence.createEntityManagerFactory("myUnit");
        entityManager = myUnit.createEntityManager();
//        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Transient()
    public Dish findByDish(int id) {
        Dish dish = entityManager.find(Dish.class, id);
        entityManager.detach(dish);
        return dish;
//     return sessionFactory.openSession().get(Dish.class, id);
    }

    @Transient
    public Map<String, Object> findByDish(String recipe) {
        Integer byRecipe = findByRecipe(recipe);
        System.out.println(byRecipe + "---------------------------");
        Query query = entityManager.createQuery("SELECT Ingredient.name, Dish.weight FROM  Dish LEFT JOIN Ingredient \n" +
                "    ON Dish.ingredient.id = Ingredient.id WHERE Dish.recipe.id = :byRecipe");
        query.setParameter("byRecipe", byRecipe);
        return query.getHints();
//     return sessionFactory.openSession().get(Dish.class, id);
    }

    @Transient
    public Integer findByRecipe(String recipe) {
        Query query = entityManager.createQuery("SELECT id FROM Recipe WHERE name = :recipe", Integer.class);
        query.setParameter("recipe", recipe);
        try {
            return  (Integer) query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
//     return sessionFactory.openSession().get(Dish.class, id);
    }

    @Transient
    public void save(Dish dish) {
        entityManager.getTransaction().begin();
        entityManager.persist(dish);
        entityManager.getTransaction().commit();
//        Session session = sessionFactory.openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.save(dish);
//        tx1.commit();
//        session.close();
    }

    @Transient
    public void update(Dish dish) {
        entityManager.getTransaction().begin();
        entityManager.merge(dish);
        entityManager.getTransaction().commit();
//        Session session = sessionFactory.openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.update(dish);
//        tx1.commit();
//        session.close();
    }

    @Transient
    public void delete(Dish dish) {
        entityManager.getTransaction().begin();
        entityManager.remove(dish);
        entityManager.getTransaction().commit();

//        Session session = sessionFactory.openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.delete(dish);
//        tx1.commit();
//        session.close();
    }

//
//    public List<Recipe> findAll() {
//        List<Recipe> recipe = (List<Recipe>) sessionFactory.openSession().createQuery("From Recipe").list();
//        return recipe;
//    }
}
