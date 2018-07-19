package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverState;

import java.util.Collection;

public interface DriverDAO {


    Driver createDriver(double hoursWorked,
                        DriverState state,
                        City currentCityId,
                        Truck currentTruckId);

    Driver update(int id,
                  double hoursWorked,
                  DriverState state,
                  City currentCityId,
                  Truck currentTruckId);

    Driver getById(int id);

    Collection<Driver> getAll();
    void delete(int id);
}
