package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class TruckDAOImpl implements TruckDAO{
    private static SessionFactory sessionFactory;
    private static TruckDAOImpl instance;


    private TruckDAOImpl(SessionFactory sessionFactory){
        TruckDAOImpl.sessionFactory = sessionFactory;
    }

    public static TruckDAOImpl getTruckDAOInstance(){
        if (instance == null){
            synchronized (TruckDAOImpl.class){
                instance = new TruckDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
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
