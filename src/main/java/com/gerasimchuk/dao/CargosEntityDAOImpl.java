package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CargoPropertiesEntity;
import com.gerasimchuk.entities.CargosEntity;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class CargosEntityDAOImpl implements CargosEntityDAO{
    private static SessionFactory sessionFactory;
    private static CargosEntityDAOImpl instance;


    private CargosEntityDAOImpl(SessionFactory sessionFactory){
        CargosEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static CargosEntityDAOImpl getCargosEntityDAOInstance(){
        if (instance == null){
            synchronized (CargosEntityDAOImpl.class){
                instance = new CargosEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }


    @Override
    public CargosEntity create(String cargoName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargosEntity cargo = new CargosEntity();
        cargo.setCargoName(cargoName);
        session.persist(cargo);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public CargosEntity update(int id, String cargoName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargosEntity cargo = session.get(CargosEntity.class, id);
        cargo.setCargoName(cargoName);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public CargosEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargosEntity cargo = session.get(CargosEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargo;
    }

    @Override
    public Collection<CargosEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<CargosEntity> cargosEntities = session.createQuery("from CargosEntity ", CargosEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return cargosEntities;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CargosEntity cargo = session.get(CargosEntity.class, id);
        session.delete(cargo);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
