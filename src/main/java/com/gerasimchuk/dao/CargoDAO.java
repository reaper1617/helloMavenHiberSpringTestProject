package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.CargoStatus;

import java.util.Collection;

public interface CargoDAO {
    Cargo create(String cargoName,
                 double weight,
                 CargoStatus status,
                 Order assignedOrder,
                 RoutePoint loadPoint,
                 RoutePoint unloadPoint);

    Cargo update(int id,
                 String cargoName,
                 double weight,
                 CargoStatus status,
                 Order assignedOrder,
                 RoutePoint loadPoint,
                 RoutePoint unloadPoint);

    Cargo getById(int id);
    Collection<Cargo> getAll();
    void delete(int id);
}
