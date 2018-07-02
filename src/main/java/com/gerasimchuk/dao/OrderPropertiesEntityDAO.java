package com.gerasimchuk.dao;

import com.gerasimchuk.entities.OrderPropertiesEntity;
import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.entities.TrucksEntity;

import java.util.Collection;

public interface OrderPropertiesEntityDAO {
    OrderPropertiesEntity create(OrdersEntity assignedOrder,
                                 String orderDate,
                                 TrucksEntity assignedTruck);

    OrderPropertiesEntity update(int id,
                                 OrdersEntity assignedOrder,
                                 String orderDate,
                                 TrucksEntity assignedTruck);


    OrderPropertiesEntity getById(int id);
    Collection<OrderPropertiesEntity> getAll();
    void delete(int id);
}
