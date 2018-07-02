package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.MapEntity;
import com.gerasimchuk.entities.MapEntityPK;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class MapEntityDAOImpl implements MapEntityDAO {
    private static SessionFactory sessionFactory;
    private static MapEntityDAOImpl instance;


    private MapEntityDAOImpl(SessionFactory sessionFactory){
        MapEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static MapEntityDAOImpl getMapEntityDAOInstance(){
        if (instance == null){
            synchronized (MapEntityDAOImpl.class){
                instance = new MapEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public MapEntity create(CitiesEntity departureCity, CitiesEntity destinationCity, double distance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MapEntity mapEntity = new MapEntity();
        mapEntity.setDepartureCity(departureCity);
        mapEntity.setDestinationCity(destinationCity);
        mapEntity.setDistance(distance);
        session.persist(mapEntity);
        transaction.commit();
        if (session.isOpen()) session.close();
        return mapEntity;
    }

    @Override
    public MapEntity update(MapEntityPK id, CitiesEntity departureCity, CitiesEntity destinationCity, double distance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MapEntity mapEntity = session.get(MapEntity.class, id);
        mapEntity.setDepartureCity(departureCity);
        mapEntity.setDestinationCity(destinationCity);
        mapEntity.setDistance(distance);
        transaction.commit();
        if (session.isOpen()) session.close();
        return mapEntity;
    }

    @Override
    public MapEntity getById(MapEntityPK id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MapEntity mapEntity = session.get(MapEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return mapEntity;
    }

    @Override
    public Collection<MapEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<MapEntity> mapEntities = session.createQuery("from MapEntity",MapEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return mapEntities;
    }

    @Override
    public void delete(MapEntityPK id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MapEntity mapEntity = session.get(MapEntity.class, id);
        session.delete(mapEntity);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
