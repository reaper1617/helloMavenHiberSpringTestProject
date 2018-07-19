package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class DriverDAOImpl implements DriverDAO {
    private SessionFactory sessionFactory;


    @Autowired
    public DriverDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Driver createDriver(double hoursWorked, DriverState state, City currentCity, Truck currentTruck) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = new Driver(hoursWorked,state,currentCity,currentTruck);
        session.persist(driver);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public Driver update(int id, double hoursWorked, DriverState state, City currentCityId, Truck currentTruckId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, id);
        driver.setHoursWorked(hoursWorked);
        driver.setState(state);
        driver.setCurrentCity(currentCityId);
        driver.setCurrentTruck(currentTruckId);
        session.saveOrUpdate(driver);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public Driver getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return driver;
    }

    @Override
    public Collection<Driver> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Driver> drivers = session.createQuery("from Drivers", Driver.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return drivers;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, id);
        session.delete(driver);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
