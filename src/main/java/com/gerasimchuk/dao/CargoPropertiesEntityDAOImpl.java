package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CargoPropertiesEntity;
import com.gerasimchuk.entities.CargosEntity;
import com.gerasimchuk.entities.RoutePointsEntity;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class CargoPropertiesEntityDAOImpl implements CargoPropertiesEntityDAO {
    private static SessionFactory sessionFactory;
    private static CargoPropertiesEntityDAOImpl instance;


    private CargoPropertiesEntityDAOImpl(SessionFactory sessionFactory){
        CargoPropertiesEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static CargoPropertiesEntityDAOImpl getCargoPropertiesEntitiyDAOInstance(){
        if (instance == null){
            synchronized (CargoPropertiesEntityDAOImpl.class){
                instance = new CargoPropertiesEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public CargoPropertiesEntity create(CargosEntity assignedCargo,
                                        RoutePointsEntity assignedRoutePoint,
                                        double weight,
                                        CargoStatus cargoStatus) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CargoPropertiesEntity cargoProperties = new CargoPropertiesEntity();
        cargoProperties.setAssignedCargo(assignedCargo);
        cargoProperties.setAssignedRoutePoint(assignedRoutePoint);
        cargoProperties.setWeight(weight);
        cargoProperties.setCargoStatus(cargoStatus);

        session.persist(cargoProperties);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargoProperties;
    }

    @Override
    public CargoPropertiesEntity update(int id,
                                        CargosEntity assignedCargo,
                                        RoutePointsEntity assignedRoutePoint,
                                        double weight,
                                        CargoStatus cargoStatus) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargoPropertiesEntity cargoProperties = session.get(CargoPropertiesEntity.class, id);
        cargoProperties.setAssignedCargo(assignedCargo);
        cargoProperties.setAssignedRoutePoint(assignedRoutePoint);
        cargoProperties.setWeight(weight);
        cargoProperties.setCargoStatus(cargoStatus);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargoProperties;
    }

    @Override
    public CargoPropertiesEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargoPropertiesEntity cargoProperties = session.get(CargoPropertiesEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargoProperties;
    }

    @Override
    public Collection<CargoPropertiesEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<CargoPropertiesEntity> cargoPropertiesEntities = session.createQuery("from CargoPropertiesEntity ", CargoPropertiesEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargoPropertiesEntities;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CargoPropertiesEntity cargoProperties = session.get(CargoPropertiesEntity.class, id);
        session.delete(cargoProperties);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
