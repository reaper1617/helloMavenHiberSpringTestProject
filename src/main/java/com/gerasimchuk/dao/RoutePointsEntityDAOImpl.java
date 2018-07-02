package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.entities.RoutePointsEntity;
import com.gerasimchuk.enums.RoutePointType;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class RoutePointsEntityDAOImpl implements RoutePointsEntityDAO {

    private static SessionFactory sessionFactory;
    private static RoutePointsEntityDAOImpl instance;


    private RoutePointsEntityDAOImpl(SessionFactory sessionFactory){
        RoutePointsEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static RoutePointsEntityDAOImpl getRoutePointsEntityDAOInstance(){
        if (instance == null){
            synchronized (RoutePointsEntityDAOImpl.class){
                instance = new RoutePointsEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public RoutePointsEntity create(RoutePointType type, OrdersEntity assignedOrder, CitiesEntity city) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePointsEntity routePoint = new RoutePointsEntity();
        routePoint.setType(type);
        routePoint.setAssignedOrder(assignedOrder);
        routePoint.setCity(city);
        session.persist(routePoint);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public RoutePointsEntity update(int id, RoutePointType type, OrdersEntity assignedOrder, CitiesEntity city) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePointsEntity routePoint = session.get(RoutePointsEntity.class, id);
        routePoint.setType(type);
        routePoint.setAssignedOrder(assignedOrder);
        routePoint.setCity(city);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public RoutePointsEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePointsEntity routePoint = session.get(RoutePointsEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoint;
    }

    @Override
    public Collection<RoutePointsEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<RoutePointsEntity> routePoints = session.createQuery("from RoutePointsEntity",RoutePointsEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return routePoints;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RoutePointsEntity routePoint = session.get(RoutePointsEntity.class, id);
        session.delete(routePoint);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
