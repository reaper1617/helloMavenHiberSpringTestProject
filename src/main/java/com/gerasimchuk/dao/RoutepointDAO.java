package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.RoutePointType;

import java.util.Collection;

public interface RoutepointDAO {
    RoutePoint create(RoutePointType type,
                      City city);

    RoutePoint update(int id,
                      RoutePointType type,
                      City city);

    RoutePoint getById(int id);
    Collection<RoutePoint> getAll();
    void delete(int id);
}
