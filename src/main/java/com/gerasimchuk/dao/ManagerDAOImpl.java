package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.enums.ManagerPosition;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.Collection;

public class ManagerDAOImpl implements ManagerDAO {
    private static SessionFactory sessionFactory;
    private static ManagerDAOImpl instance;


    private ManagerDAOImpl(SessionFactory sessionFactory){
        ManagerDAOImpl.sessionFactory = sessionFactory;
    }

    public static ManagerDAOImpl getManagerDAOInstance(){
        if (instance == null){
            synchronized (ManagerDAOImpl.class){
                instance = new ManagerDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public Manager create(ManagerPosition managerPosition) {
        Session  session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Manager manager = new Manager(managerPosition);
        session.persist(manager);
        transaction.commit();
        if (session.isOpen()) session.close();
        return manager;
    }

    @Override
    public Manager update(int id, double hoursWorked, ManagerPosition managerPosition) {
        Session  session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Manager manager = session.get(Manager.class, id);
        manager.setManagerPosition(managerPosition);
        session.saveOrUpdate(manager);
        transaction.commit();
        if (session.isOpen()) session.close();
        return manager;
    }

    @Override
    public Manager getById(int id) {
        Session  session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Manager manager = session.get(Manager.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return manager;
    }

    @Override
    public Collection<Manager> getAll() {
        Session  session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Manager> managers = session.createQuery("from Managers", Manager.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return managers;
    }

    @Override
    public void delete(int id) {
        Session  session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Manager manager = session.get(Manager.class, id);
        session.delete(manager);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
