package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.TrucksEntity;
import com.gerasimchuk.enums.TruckState;

import java.util.Collection;

public interface TrucksEntityDAO {
    TrucksEntity create(String registrationNumber,
                        int shift,
                        double capacity,
                        TruckState state,
                        CitiesEntity currentCity);

    TrucksEntity update(int id,
                        String registrationNumber,
                        int shift,
                        double capacity,
                        TruckState state,
                        CitiesEntity currentCity);

    TrucksEntity getById(int id);
    Collection<TrucksEntity> getAll();
    void delete(int id);
}
