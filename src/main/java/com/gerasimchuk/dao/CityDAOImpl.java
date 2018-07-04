package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class CityDAOImpl implements CityDAO {
    private static SessionFactory sessionFactory;
    private static CityDAOImpl instance;


    private CityDAOImpl(SessionFactory sessionFactory){
        CityDAOImpl.sessionFactory = sessionFactory;
    }
    public static CityDAOImpl getCityDAOInstance(){
        if (instance == null){
            synchronized (CityDAOImpl.class){
                instance = new CityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public City create(String cityName, CityHasAgency hasAgency) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City city = new City(cityName,hasAgency);
        session.persist(city);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public City update(int id, String cityName, CityHasAgency hasAgency) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City city = session.get(City.class, id);
        city.setCityName(cityName);
        city.setHasAgency(hasAgency);
        session.saveOrUpdate(city);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public City getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City city = session.get(City.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return city;
    }

    @Override
    public Collection<City> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<City> cities = session.createQuery("from Cities", City.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return cities;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City city = session.get(City.class, id);
        session.delete(city);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
