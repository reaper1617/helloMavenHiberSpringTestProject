package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class CitiesEntityDAOImpl implements CitiesEntityDAO {
    private static SessionFactory sessionFactory;
    private static CitiesEntityDAOImpl instance;


    private CitiesEntityDAOImpl(SessionFactory sessionFactory){
        CitiesEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static CitiesEntityDAOImpl getCitiesEntityDAOInstance(){
        if (instance == null){
            synchronized (CitiesEntityDAOImpl.class){
                instance = new CitiesEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public CitiesEntity create(String cityName, CityHasAgency hasAgency) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CitiesEntity city = new CitiesEntity();
        city.setCityName(cityName);
        city.setHasAgency(hasAgency);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public CitiesEntity update(int id, String cityName, CityHasAgency hasAgency) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CitiesEntity city = session.get(CitiesEntity.class, id);
        city.setCityName(cityName);
        city.setHasAgency(hasAgency);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public CitiesEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CitiesEntity city = session.get(CitiesEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public Collection<CitiesEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<CitiesEntity> citiesEntities = session.createQuery("from CitiesEntity ", CitiesEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return citiesEntities;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CitiesEntity city = session.get(CitiesEntity.class, id);
        session.delete(city);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
