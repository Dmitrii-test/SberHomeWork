package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.dmitrii.hibernate.model.Ingredients;

@Component
public class IngredientsDAO {
    final SessionFactory getSessionFactory;

    public IngredientsDAO(SessionFactory getSessionFactory) {
        this.getSessionFactory = getSessionFactory;
    }

    public Ingredients findById(int id) {
        return getSessionFactory.openSession().get(Ingredients.class, id);
    }
    public void save(Ingredients ingredients) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(ingredients);
        tx1.commit();
        session.close();
    }

    public void update(Ingredients ingredients) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(ingredients);
        tx1.commit();
        session.close();
    }

    public void delete(Ingredients ingredients) {
        Session session = getSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(ingredients);
        tx1.commit();
        session.close();
    }
}
