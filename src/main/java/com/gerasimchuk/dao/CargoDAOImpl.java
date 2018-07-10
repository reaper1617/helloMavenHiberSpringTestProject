package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.Collection;

public class CargoDAOImpl implements  CargoDAO{

    private static SessionFactory sessionFactory;
    private static CargoDAOImpl instance;


    private CargoDAOImpl(SessionFactory sessionFactory){
        CargoDAOImpl.sessionFactory = sessionFactory;
    }

    public static CargoDAOImpl getCargoDAOInstance(){
        if (instance == null){
            synchronized (CargoDAOImpl.class){
                instance = new CargoDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public Cargo create(String cargoName, double weight, CargoStatus status, RoutePoint loadPoint, RoutePoint unloadPoint) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cargo cargo = new Cargo(cargoName,
                                weight,
                                status,
                                loadPoint,
                                unloadPoint);

        session.persist(cargo);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public Cargo update(int id, String cargoName, double weight, CargoStatus status, Order assignedOrder, RoutePoint loadPoint, RoutePoint unloadPoint) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cargo cargo = session.get(Cargo.class, id);
        cargo.setCargoName(cargoName);
        cargo.setWeight(weight);
        cargo.setStatus(status);
        cargo.setAssignedOrder(assignedOrder);
        cargo.setLoadPoint(loadPoint);
        cargo.setUnloadPoint(unloadPoint);
        session.saveOrUpdate(cargo);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public Cargo getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cargo cargo = session.get(Cargo.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public Collection<Cargo> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Cargo> cargos = session.createQuery("from Cargos", Cargo.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargos;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cargo cargo = session.get(Cargo.class, id);
        session.delete(cargo);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
