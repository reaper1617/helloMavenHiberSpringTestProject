package com.gerasimchuk.dao;

import com.gerasimchuk.enums.ManagerPosition;
import com.gerasimchuk.entities.ManagersEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class ManagersEntityDAOImpl implements ManagersEntityDAO {

    private static SessionFactory sessionFactory;
    private static ManagersEntityDAOImpl instance;


    private ManagersEntityDAOImpl(SessionFactory sessionFactory){
        ManagersEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static ManagersEntityDAOImpl getManagersEntityDAOInstance(){
        if (instance == null){
            synchronized (ManagersEntityDAOImpl.class){
                instance = new ManagersEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public ManagersEntity create(ManagerPosition managerPosition) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ManagersEntity manager = new ManagersEntity();
        manager.setManagerPosition(managerPosition);
        session.persist(manager);
        transaction.commit();
        if (session.isOpen()) session.close();;
        return  manager;
    }

    @Override
    public ManagersEntity update(int id, ManagerPosition managerPosition) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ManagersEntity manager = sessionFactory.openSession().get(ManagersEntity.class,id);
        manager.setManagerPosition(managerPosition);
        transaction.commit();
        if (session.isOpen()) session.close();
        return  manager;
    }

    @Override
    public ManagersEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ManagersEntity managersEntity = session.get(ManagersEntity.class,id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return  managersEntity;
    }

    @Override
    public Collection<ManagersEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<ManagersEntity> collection = session.createQuery("from ManagersEntity", ManagersEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return collection;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ManagersEntity managersEntity = session.get(ManagersEntity.class, id);
        session.delete(managersEntity);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
