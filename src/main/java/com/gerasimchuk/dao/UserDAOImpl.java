package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.Collection;

public class UserDAOImpl implements UserDAO {

    private static SessionFactory sessionFactory;
    private static UserDAOImpl instance;


    private UserDAOImpl(SessionFactory sessionFactory){
        UserDAOImpl.sessionFactory = sessionFactory;
    }

    public static UserDAOImpl getUserDAOInstance(){
        if (instance == null){
            synchronized (UserDAOImpl.class){
                instance = new UserDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
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
        user.setLastname(lastname);
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
        user.setLastname(lastname);
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
