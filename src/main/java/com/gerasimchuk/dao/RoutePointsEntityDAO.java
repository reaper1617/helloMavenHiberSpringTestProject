package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.OrdersEntity;
import com.gerasimchuk.entities.RoutePointsEntity;
import com.gerasimchuk.enums.RoutePointType;

import java.util.Collection;

public interface RoutePointsEntityDAO {
    RoutePointsEntity create(RoutePointType type,
                            OrdersEntity assignedOrderId,
                            CitiesEntity cityId);

    RoutePointsEntity update(int id,
                             RoutePointType type,
                             OrdersEntity assignedOrder,
                             CitiesEntity city);

    RoutePointsEntity getById(int id);
    Collection<RoutePointsEntity> getAll();
    void delete(int id);
}
