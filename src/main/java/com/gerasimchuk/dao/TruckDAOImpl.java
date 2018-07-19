package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TruckDAOImpl implements TruckDAO{
    private  SessionFactory sessionFactory;


    @Autowired
    public TruckDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Truck create(String registrationNumber, int shift, double capacity, TruckState state, City currentCity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = new Truck(registrationNumber,  shift,  capacity,  state,  currentCity);
        session.persist(truck);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public Truck update(int id, String registrationNumber, int shift, double capacity, TruckState state, City currentCity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = session.get(Truck.class, id);
        truck.setRegistrationNumber(registrationNumber);
        truck.setShift(shift);
        truck.setCapacity(capacity);
        truck.setState(state);
        truck.setCurrentCity(currentCity);
        session.saveOrUpdate(truck);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public Truck getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = session.get(Truck.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public Truck getByRegistrationNumber(String regNum) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Truck foundTruck = null;
        Collection<Truck> trucks = session.createQuery("from Trucks", Truck.class).getResultList();
        if (trucks != null) {
            for (Truck t : trucks) {
                if (t.getRegistrationNumber().equals(regNum)) {
                    foundTruck = t;
                    break;
                }
            }
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return foundTruck;
    }

    @Override
    public Collection<Truck> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Truck> trucks = session.createQuery("from Trucks ", Truck.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return trucks;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = session.get(Truck.class, id);
        session.delete(truck);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
