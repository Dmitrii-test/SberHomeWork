package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.dmitrii.hibernate.model.Ingredient;

@Component
public class IngredientsDAO {

    final SessionFactory sessionFactory;

    public IngredientsDAO() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Ingredient findById(int id) {
        return sessionFactory.openSession().get(Ingredient.class, id);
    }
    public void save(Ingredient ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(ingredients);
        tx1.commit();
        session.close();
    }

    public void update(Ingredient ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(ingredients);
        tx1.commit();
        session.close();
    }

    public void delete(Ingredient ingredients) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(ingredients);
        tx1.commit();
        session.close();
    }
}
