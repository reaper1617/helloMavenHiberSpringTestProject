package com.gerasimchuk.dao;

import com.gerasimchuk.entities.UsersEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;


public class UsersEntityDAOImpl implements UsersEntityDAO {


    private static SessionFactory sessionFactory;
    private static UsersEntityDAOImpl instance;


    private UsersEntityDAOImpl(SessionFactory sessionFactory){
        UsersEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static UsersEntityDAO getUsersEntityDAOInstance(){
        if (instance == null){
            synchronized (UsersEntityDAOImpl.class){
                instance = new UsersEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    public UsersEntity create(String userName,
                        String userMiddleName,
                        String userLastname,
                        String userPassword,
                        int userManager,
                        int userDriver) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user= new UsersEntity();

        user.setUserName(userName);
        user.setUserMiddleName(userMiddleName);
        user.setUserLastname(userLastname);
        user.setUserPassword(userPassword);
        user.setUserManager(userManager);
        user.setUserDriver(userDriver);

        session.persist(user);
        transaction.commit();
        if (session.isOpen()) session.close();
        return user;
    }

    public UsersEntity update(int id,
                        String userName,
                        String userMiddleName,
                        String userLastname,
                        String userPassword,
                        int userManager,
                        int userDriver) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user = session.get(UsersEntity.class, id);
        user.setUserName(userName);
        user.setUserMiddleName(userMiddleName);
        user.setUserLastname(userLastname);
        user.setUserPassword(userPassword);
        user.setUserManager(userManager);
        user.setUserDriver(userDriver);
        session.saveOrUpdate(user);
        transaction.commit();
        if (session.isOpen()) session.close();
        return user;
    }

    public UsersEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user = session.get(UsersEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return user;
    }

    public Collection<UsersEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<UsersEntity> collection = session.createQuery("from UsersEntity", UsersEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return collection;
    }

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user = session.get(UsersEntity.class, id);
        session.delete(user);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
