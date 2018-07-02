package com.gerasimchuk.dao;

import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.enums.OrderState;

import java.util.Collection;

public interface OrdersEntityDAO {
    OrdersEntity create(OrderState orderState);

    OrdersEntity update(int id,
                        OrderState orderState);

    OrdersEntity getById(int id);
    Collection<OrdersEntity> getAll();
    void delete(int id);
}
