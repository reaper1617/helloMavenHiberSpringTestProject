package com.gerasimchuk.dao;

import com.gerasimchuk.entities.UsersEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


public class UsersEntityDAOImpl implements UsersEntityDAO {


    private static SessionFactory sessionFactory;
    private static UsersEntityDAOImpl instance;

    private UsersEntityDAOImpl(SessionFactory sessionFactory){
        UsersEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static UsersEntityDAO getUsersEntityDAOInsance(){
        if (instance == null){
            synchronized (UsersEntityDAOImpl.class){
                instance = new UsersEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    public UsersEntity create(String uname, String lastname, String unumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user= new UsersEntity();
        user.setUname(uname);
        user.setLastname(lastname);
        user.setUnumber(unumber);
        session.persist(user);
        transaction.commit();
        if (session.isOpen()) session.close();
        return user;
    }

    public UsersEntity update(int id, String uname, String lastname, String unumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersEntity user = session.get(UsersEntity.class, id);
        user.setUname(uname);
        user.setLastname(lastname);
        user.setUnumber(unumber);
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
