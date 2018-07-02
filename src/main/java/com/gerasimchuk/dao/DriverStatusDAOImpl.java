package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.DriversEntity;
import com.gerasimchuk.entities.TrucksEntity;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.entities.DriverStatusEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class DriverStatusDAOImpl implements DriverStatusEntityDAO {

    private static SessionFactory sessionFactory;
    private static DriverStatusDAOImpl instance;


    private DriverStatusDAOImpl(SessionFactory sessionFactory){
        DriverStatusDAOImpl.sessionFactory = sessionFactory;
    }

    public static DriverStatusDAOImpl getDriverStatusEntityDAOInstance(){
        if (instance == null){
            synchronized (DriverStatusDAOImpl.class){
                instance = new DriverStatusDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public DriverStatusEntity create(DriversEntity assignedDriver, DriverState state, CitiesEntity currentCity, TrucksEntity currentTruck) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriverStatusEntity driverStatus = new DriverStatusEntity();
        driverStatus.setAssignedDriver(assignedDriver);
        driverStatus.setState(state);
        driverStatus.setCurrentCity(currentCity);
        driverStatus.setCurrentTruck(currentTruck);
        session.persist(driverStatus);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driverStatus;
    }

    @Override
    public DriverStatusEntity update(int id, DriversEntity assignedDriver, DriverState state, CitiesEntity currentCity, TrucksEntity currentTruck) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriverStatusEntity driverStatus = session.get(DriverStatusEntity.class, id);
        driverStatus.setAssignedDriver(assignedDriver);
        driverStatus.setState(state);
        driverStatus.setCurrentCity(currentCity);
        driverStatus.setCurrentTruck(currentTruck);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driverStatus;
    }

    @Override
    public DriverStatusEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriverStatusEntity driverStatus = session.get(DriverStatusEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driverStatus;
    }

    @Override
    public Collection<DriverStatusEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<DriverStatusEntity> driverStatusEntities = session.createQuery("from DriverStatusEntity ",
                                                                                    DriverStatusEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return driverStatusEntities;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DriverStatusEntity driverStatus = session.get(DriverStatusEntity.class, id);
        session.delete(driverStatus);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
