package com.gerasimchuk.dao;

import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class OrdersEntityDAOImpl implements OrdersEntityDAO {
    private static SessionFactory sessionFactory;
    private static OrdersEntityDAOImpl instance;


    private OrdersEntityDAOImpl(SessionFactory sessionFactory){
        OrdersEntityDAOImpl.sessionFactory = sessionFactory;
    }

    public static OrdersEntityDAOImpl getOrdersEntityDAOInstance(){
        if (instance == null){
            synchronized (OrdersEntityDAOImpl.class){
                instance = new OrdersEntityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public OrdersEntity create(OrderState orderState) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrdersEntity order = new OrdersEntity();
        order.setOrderState(orderState);
        session.persist(order);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public OrdersEntity update(int id, OrderState orderState) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrdersEntity order = session.get(OrdersEntity.class, id);
        order.setOrderState(orderState);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public OrdersEntity getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrdersEntity order = session.get(OrdersEntity.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public Collection<OrdersEntity> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<OrdersEntity> orders = session.createQuery("from OrdersEntity", OrdersEntity.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return orders;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        OrdersEntity order = session.get(OrdersEntity.class, id);
        session.delete(order);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
