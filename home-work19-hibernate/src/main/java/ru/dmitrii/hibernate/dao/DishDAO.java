package ru.dmitrii.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.dmitrii.hibernate.model.Dish;

import javax.persistence.*;

@Service
public class DishDAO {

//    final SessionFactory sessionFactory;

    private EntityManager entityManager;

    public DishDAO() {
        EntityManagerFactory myUnit = Persistence.createEntityManagerFactory("myUnit");
        entityManager = myUnit.createEntityManager();
//        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Dish findById(int id) {
        Dish dish = entityManager.find(Dish.class, id);
        entityManager.detach(dish);
        return dish;
//     return sessionFactory.openSession().get(Dish.class, id);
    }

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
