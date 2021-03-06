package com.gerasimchuk.service;

import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;

import java.util.Collection;

public interface CityService {


    boolean checkCityByName(String cityName);

    boolean checkCityById(int id);
}
