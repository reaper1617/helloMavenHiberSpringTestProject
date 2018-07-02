package com.gerasimchuk.dao;

import com.gerasimchuk.entities.OrderPropertiesEntity;
import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.entities.TrucksEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class OrderPropertiesEntityDAOImpl implements OrderPropertiesEntityDAO {


    private static SessionFactory sessionFactory;
    private static OrderPropertiesEntityDAOImpl instance;


    private OrderPropertiesEntityDAOImpl(SessionFactory sessionFactory){
        OrderPropertiesEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static OrderPropertiesEntityDAOImpl getDriverStatusEntityDAOInstance(){
        if (instance == null){
            synchronized (OrderPropertiesEntityDAOImpl.class){
                instance = new OrderPropertiesEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }
    @Override
    public OrderPropertiesEntity create(OrdersEntity assignedOrder,
                                        String orderDate,
                                        TrucksEntity assignedTruck) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrderPropertiesEntity properties = new OrderPropertiesEntity();
        properties.setAssignedOrder(assignedOrder);
        properties.setOrderDate(orderDate);
        properties.setAssignedTruck(assignedTruck);
        session.persist(properties);
        transaction.commit();
        if (session.isOpen()) session.close();
        return properties;
    }

    @Override
    public OrderPropertiesEntity update(int id,
                                        OrdersEntity assignedOrder,
                                        String orderDate,
                                        TrucksEntity assignedTruck) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrderPropertiesEntity properties = session.get(OrderPropertiesEntity.class, id);
        properties.setAssignedOrder(assignedOrder);
        properties.setOrderDate(orderDate);
        properties.setAssignedTruck(assignedTruck);
        transaction.commit();
        if (session.isOpen()) session.close();
        return properties;
    }

    @Override
    public OrderPropertiesEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrderPropertiesEntity properties = session.get(OrderPropertiesEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return properties;
    }

    @Override
    public Collection<OrderPropertiesEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<OrderPropertiesEntity> properties = session.createQuery("from OrderPropertiesEntity ", OrderPropertiesEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return properties;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrderPropertiesEntity properties = session.get(OrderPropertiesEntity.class, id);
        session.delete(properties);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
