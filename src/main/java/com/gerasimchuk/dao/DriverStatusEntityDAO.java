package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.DriversEntity;
import com.gerasimchuk.entities.TrucksEntity;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.entities.DriverStatusEntity;

import java.util.Collection;

public interface DriverStatusEntityDAO {

        DriverStatusEntity create( DriversEntity assignedDriver,
                                   DriverState state,
                                   CitiesEntity currentCity,
                                   TrucksEntity currentTruck);

        DriverStatusEntity update(int id,
                                  DriversEntity assignedDriver,
                                  DriverState state,
                                  CitiesEntity currentCity,
                                  TrucksEntity currentTruck);

        DriverStatusEntity getById(int id);
        Collection<DriverStatusEntity> getAll();
        void delete(int id);
}
