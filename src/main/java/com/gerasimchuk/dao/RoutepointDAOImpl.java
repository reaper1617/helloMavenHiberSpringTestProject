package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.RoutePointType;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class RoutepointDAOImpl implements RoutepointDAO {

    private static SessionFactory sessionFactory;
    private static RoutepointDAOImpl instance;


    private RoutepointDAOImpl(SessionFactory sessionFactory){
        RoutepointDAOImpl.sessionFactory = sessionFactory;
    }

    public static RoutepointDAOImpl getRoutepointDAOInstance(){
        if (instance == null){
            synchronized (RoutepointDAOImpl.class){
                instance = new RoutepointDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public RoutePoint create(RoutePointType type, City city) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePoint routePoint = new RoutePoint(type,city);
        session.persist(routePoint);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public RoutePoint update(int id, RoutePointType type, City city) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePoint routePoint = session.get(RoutePoint.class, id);
        routePoint.setType(type);
        routePoint.setCity(city);
        session.saveOrUpdate(routePoint);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public RoutePoint getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePoint routePoint = session.get(RoutePoint.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public Collection<RoutePoint> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<RoutePoint> routePoints = session.createQuery("from RoutePoint", RoutePoint.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoints;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePoint routePoint = session.get(RoutePoint.class, id);
        session.delete(routePoint);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
