package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Repository
public class OrderDAOImpl implements OrderDAO{
    private  SessionFactory sessionFactory;

    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public Order getByDescription(String description) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order findedOrder = null;
        Collection<Order> orders = session.createQuery("from Orders", Order.class).getResultList();
        if (orders == null) return null;
        for(Order o: orders){
            if (o.getOrderDescription().equals(description)) findedOrder = o;
        }
        transaction.commit();
        if (session.isOpen()) session.close();
        return findedOrder;
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
