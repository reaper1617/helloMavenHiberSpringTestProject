package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderState;

import java.sql.Timestamp;
import java.util.Collection;

public interface OrderDAO {
    Order create(OrderState orderState,
                 String orderDescription,
                 Timestamp orderDate,
                 Truck assignedTruck);

    Order update(int id,
                 OrderState orderState,
                 String orderDescription,
                 Timestamp orderDate,
                 Truck assignedTruck);

    Order getById(int id);
    Collection<Order> getAll();
    void delete(int id);
}
