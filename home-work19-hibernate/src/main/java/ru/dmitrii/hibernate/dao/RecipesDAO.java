package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.dmitrii.hibernate.model.Ingredient;
import ru.dmitrii.hibernate.model.Recipe;

import java.util.List;


@Service
public class RecipesDAO {

    final SessionFactory sessionFactory;

    public RecipesDAO() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Recipe findById(int id) {
        return sessionFactory.openSession().get(Recipe.class, id);
    }
    public void save(Recipe recipes) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(recipes);
        tx1.commit();
        session.close();
    }

    public void update(Recipe recipes) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(recipes);
        tx1.commit();
        session.close();
    }

    public void delete(Recipe recipes) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(recipes);
        tx1.commit();
        session.close();
    }

    public List<Recipe> findAll() {
        List<Recipe> recipe = (List<Recipe>) sessionFactory.openSession().createQuery("From Recipe").list();
        return recipe;
    }

}
