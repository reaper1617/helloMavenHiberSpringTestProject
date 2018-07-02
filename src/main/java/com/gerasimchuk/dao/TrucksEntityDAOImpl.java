package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.TrucksEntity;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class TrucksEntityDAOImpl implements TrucksEntityDAO {
    private static SessionFactory sessionFactory;
    private static TrucksEntityDAOImpl instance;


    private TrucksEntityDAOImpl(SessionFactory sessionFactory){
        TrucksEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static TrucksEntityDAOImpl getTrucksEntityDAOInstance(){
        if (instance == null){
            synchronized (TrucksEntityDAOImpl.class){
                instance = new TrucksEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public TrucksEntity create(String registrationNumber,
                               int shift,
                               double capacity,
                               TruckState state,
                               CitiesEntity currentCity) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrucksEntity truck = new TrucksEntity();
        truck.setRegistrationNumber(registrationNumber);
        truck.setShift(shift);
        truck.setCapacity(capacity);
        truck.setState(state);
        truck.setCurrentCityId(currentCity);
        session.persist(truck);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public TrucksEntity update(int id,
                               String registrationNumber,
                               int shift,
                               double capacity,
                               TruckState state,
                               CitiesEntity currentCity) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrucksEntity truck = session.get(TrucksEntity.class, id);
        truck.setRegistrationNumber(registrationNumber);
        truck.setShift(shift);
        truck.setCapacity(capacity);
        truck.setState(state);
        truck.setCurrentCityId(currentCity);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public TrucksEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrucksEntity truck = session.get(TrucksEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return truck;
    }

    @Override
    public Collection<TrucksEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<TrucksEntity> trucks  = session.createQuery("from TrucksEntity", TrucksEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return trucks;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrucksEntity truck = session.get(TrucksEntity.class, id);
        session.delete(truck);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
