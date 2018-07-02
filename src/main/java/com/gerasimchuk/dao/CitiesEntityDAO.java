package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.enums.CityHasAgency;

import java.util.Collection;

public interface CitiesEntityDAO {
    CitiesEntity create(String cityName,
                        CityHasAgency hasAgency);

    CitiesEntity update(int id,
                        String cityName,
                        CityHasAgency hasAgency);

    CitiesEntity getById(int id);
    Collection<CitiesEntity> getAll();
    void delete(int id);
}
