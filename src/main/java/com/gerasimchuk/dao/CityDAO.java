package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;

import java.util.Collection;

public interface CityDAO {
    City create(String cityName, CityHasAgency hasAgency);

    City update(int id, String cityName, CityHasAgency hasAgency);

    City getById(int id);

    City getByName(String cityName);

    Collection<City> getAll();
    void delete(int id);
}
