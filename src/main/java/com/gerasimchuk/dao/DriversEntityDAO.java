package com.gerasimchuk.dao;

import com.gerasimchuk.entities.DriversEntity;

import java.util.Collection;

public interface DriversEntityDAO {
    DriversEntity create(double houseWorked);

    DriversEntity update(int id,
                         double houseWorked);

    DriversEntity getById(int id);
    Collection<DriversEntity> getAll();
    void delete(int id);
}
