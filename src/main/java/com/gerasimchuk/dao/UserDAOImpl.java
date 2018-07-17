package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User createManager(String userName, String middleName, String lastname, String password, Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(userName, middleName, lastname, password, UserRole.MANAGER, manager);
        session.persist(user);
        transaction.commit();
        if (session.isOpen())session.close();
        return user;
    }

    @Override
    public User createDriver(String userName, String middleName, String lastname, String password, Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(userName, middleName, lastname, password, UserRole.DRIVER, driver);
        session.persist(user);
        transaction.commit();
        if (session.isOpen())session.close();
        return user;
    }

    @Override
    public User updateManager(int id, String userName, String middleName, String lastname, String password, Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        user.setUserName(userName);
        user.setMiddleName(middleName);
        user.setLastName(lastname);
        user.setPassword(password);
        user.setManager(manager);
        session.persist(user);
        transaction.commit();
        if (session.isOpen())session.close();
        return user;
    }

    @Override
    public User updateDriver(int id, String userName, String middleName, String lastname, String password, Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        user.setUserName(userName);
        user.setMiddleName(middleName);
        user.setLastName(lastname);
        user.setPassword(password);
        user.setDriver(driver);
        session.saveOrUpdate(user);
        transaction.commit();
        if (session.isOpen())session.close();
        return user;
    }

    @Override
    public User getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        transaction.commit();
        if (session.isOpen())session.close();
        return user;
    }

    @Override
    public User getByFullName(String name, String middleName, String lastName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User result = null;
        Collection<User> users = session.createQuery("from Users", User.class).getResultList();
        for(User u: users){
            if (u.getUserName().equals(name) &&
                    u.getMiddleName().equals(middleName) &&
                    u.getLastName().equals(lastName)){
                result = u;
                break;
            }
        }
        transaction.commit();
        if (session.isOpen())session.close();
        return result;
    }

    @Override
    public Collection<User> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<User> users = session.createQuery("from Users", User.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return users;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        if (session.isOpen())session.close();
    }
}
