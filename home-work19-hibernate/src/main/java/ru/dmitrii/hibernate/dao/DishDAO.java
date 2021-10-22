package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.dmitrii.hibernate.model.Dish;
import ru.dmitrii.hibernate.model.Recipe;

@Service
public class DishDAO {

    final SessionFactory sessionFactory;

    public DishDAO() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Dish findById(int id) {
        return sessionFactory.openSession().get(Dish.class, id);
    }
    public void save(Dish dish) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(dish);
        tx1.commit();
        session.close();
    }

    public void update(Dish dish) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(dish);
        tx1.commit();
        session.close();
    }

    public void delete(Dish dish) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(dish);
        tx1.commit();
        session.close();
    }

//
//    public List<Recipe> findAll() {
//        List<Recipe> recipe = (List<Recipe>) sessionFactory.openSession().createQuery("From Recipe").list();
//        return recipe;
//    }
}
