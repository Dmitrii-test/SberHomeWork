package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.dmitrii.hibernate.model.Ingredients;
import ru.dmitrii.hibernate.model.Recipes;

import javax.persistence.EntityManager;

@Repository("recipesDAO")
public class RecipesDAO {


    final EntityManager entityManager;
    final SessionFactory getSessionFactory;

    public RecipesDAO(EntityManager entityManager, SessionFactory getSessionFactory) {
        this.entityManager = entityManager;
        this.getSessionFactory = getSessionFactory;
    }

    public Recipes findById(int id) {
        return getSessionFactory.openSession().get(Recipes.class, id);
    }
    public void save(Recipes recipes) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(recipes);
        tx1.commit();
        session.close();
    }

    public void update(Recipes recipes) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(recipes);
        tx1.commit();
        session.close();
    }

    public void delete(Recipes recipes) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(recipes);
        tx1.commit();
        session.close();
    }

    public Ingredients findIngredientsById(int id) {
        return getSessionFactory.openSession().get(Ingredients.class, id);
    }
}
