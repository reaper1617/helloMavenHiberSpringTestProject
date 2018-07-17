package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.RoutePointType;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class RoutepointDAOImpl implements RoutepointDAO {

    private  SessionFactory sessionFactory;

    @Autowired
    public RoutepointDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public RoutePoint getByNameAndType(String name, RoutePointType type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<RoutePoint> routePoints = session.createQuery("from RoutePoint", RoutePoint.class).getResultList();
        RoutePoint res = null;
        if (routePoints!=null){
            for(RoutePoint r: routePoints){
                if (r.getCity().getCityName().equals(name) && r.getType() == type) res = r;
            }
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return res;
    }

    @Override
    public RoutePoint getByCityId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<RoutePoint> routePoints = session.createQuery("from RoutePoint", RoutePoint.class).getResultList();
        RoutePoint res = null;
        if (routePoints!=null){
            for(RoutePoint rp: routePoints){
                if (rp.getCity().getId() == id) res = rp;
            }
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return res;
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
