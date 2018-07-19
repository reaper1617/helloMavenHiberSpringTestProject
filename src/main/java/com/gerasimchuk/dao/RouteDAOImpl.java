package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class RouteDAOImpl implements RouteDAO {
    private  SessionFactory sessionFactory;

    @Autowired
    public RouteDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Route create(City departureCity, City destinationCity, double distance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Route route = new Route(departureCity, destinationCity, distance);
        session.persist(route);
        transaction.commit();
        if (session.isOpen()) session.close();
        return route;
    }

    @Override
    public Route update(int id, City departureCity, City destinationCity, double distance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Route route = session.get(Route.class, id);
        route.setDepartureCity(departureCity);
        route.setDestinationCity(destinationCity);
        route.setDistance(distance);
        session.saveOrUpdate(route);
        transaction.commit();
        if (session.isOpen()) session.close();
        return route;
    }

    @Override
    public Route getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Route route = session.get(Route.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return route;
    }

    @Override
    public Route getByCities(City cityFrom, City cityTo) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Route> routes = session.createQuery("from Route", Route.class).getResultList();

        Route foundedRoute = null;
        for(Route r: routes){
            if (r.getDepartureCity().equals(cityFrom) && r.getDestinationCity().equals(cityTo)) foundedRoute = r;
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return foundedRoute;
    }

    @Override
    public Collection<Route> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Route> routes = session.createQuery("from Route", Route.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return routes;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Route route = session.get(Route.class, id);
        session.delete(route);
        transaction.commit();
    }
}
