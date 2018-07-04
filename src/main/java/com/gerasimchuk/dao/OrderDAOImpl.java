package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.Collection;

public class OrderDAOImpl implements OrderDAO{
    private static SessionFactory sessionFactory;
    private static OrderDAOImpl instance;


    private OrderDAOImpl(SessionFactory sessionFactory){
        OrderDAOImpl.sessionFactory = sessionFactory;
    }
    public static OrderDAOImpl getOrderDAOInstance(){
        if (instance == null){
            synchronized (OrderDAOImpl.class){
                instance = new OrderDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
            }
        }
        return instance;
    }

    @Override
    public Order create(OrderState orderState, String orderDescription, Timestamp orderDate, Truck assignedTruck) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = new Order( orderState,  orderDescription,  orderDate,  assignedTruck);
        session.persist(order);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public Order update(int id, OrderState orderState, String orderDescription, Timestamp orderDate, Truck assignedTruck) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, id);
        order.setOrderState(orderState);
        order.setOrderDescription(orderDescription);
        order.setOrderDate(orderDate);
        order.setAssignedTruck(assignedTruck);
        session.saveOrUpdate(order);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public Order getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, id);
        transaction.commit();
        if (session.isOpen()) session.close();
        return order;
    }

    @Override
    public Collection<Order> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Collection<Order> orders = session.createQuery("from Orders", Order.class).getResultList();
        transaction.commit();
        if (session.isOpen()) session.close();
        return orders;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, id);
        session.delete(order);
        transaction.commit();
        if (session.isOpen()) session.close();
    }
}
