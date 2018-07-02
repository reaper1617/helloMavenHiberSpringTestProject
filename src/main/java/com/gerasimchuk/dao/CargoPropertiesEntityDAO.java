package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CargoPropertiesEntity;
import com.gerasimchuk.entities.CargosEntity;
import com.gerasimchuk.entities.RoutePointsEntity;
import com.gerasimchuk.enums.CargoStatus;

import java.util.Collection;

public interface CargoPropertiesEntityDAO {

    CargoPropertiesEntity create( CargosEntity assignedCargoId,
                                  RoutePointsEntity assignedRoutePointId,
                                  double weight,
                                  CargoStatus cargoStatus);

    CargoPropertiesEntity update(int id,
                                 CargosEntity assignedCargoId,
                                 RoutePointsEntity assignedRoutePointId,
                                 double weight,
                                 CargoStatus cargoStatus);

    CargoPropertiesEntity getById(int id);
    Collection<CargoPropertiesEntity> getAll();
    void delete(int id);
}
