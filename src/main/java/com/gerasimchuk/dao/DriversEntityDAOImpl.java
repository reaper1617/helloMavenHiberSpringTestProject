package com.gerasimchuk.dao;

import com.gerasimchuk.entities.DriversEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.Collection;

public class DriversEntityDAOImpl implements DriversEntityDAO {
    private static SessionFactory sessionFactory;
    private static DriversEntityDAOImpl instance;


    private DriversEntityDAOImpl(SessionFactory sessionFactory){
        DriversEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static DriversEntityDAO getDriversEntityDAOInstance(){
        if (instance == null){
            synchronized (DriversEntityDAOImpl.class){
                instance = new DriversEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public DriversEntity create(double hoursWorked) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriversEntity driver = new DriversEntity();
        driver.setHoursWorked(hoursWorked);
        session.persist(driver);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public DriversEntity update(int id, double hoursWorked) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriversEntity driver = session.get(DriversEntity.class,id);
        driver.setHoursWorked(hoursWorked);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public DriversEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriversEntity driver = session.get(DriversEntity.class,id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public Collection<DriversEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<DriversEntity> collection = session.createQuery("from DriversEntity ", DriversEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return collection;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriversEntity driver = session.get(DriversEntity.class, id);
        session.delete(driver);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
