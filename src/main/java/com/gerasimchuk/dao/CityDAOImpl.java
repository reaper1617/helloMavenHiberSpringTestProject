package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CityDAOImpl implements CityDAO {
    private SessionFactory sessionFactory;


    @Autowired
    public CityDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public City getByName(String cityName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        City foundCity = null;
        Collection<City> cities = session.createQuery("from Cities", City.class).getResultList();
        if (cities != null) {
            for (City c : cities) {
                if (c.getCityName().equals(cityName)) foundCity = c;
            }
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return foundCity;
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
