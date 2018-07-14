package com.gerasimchuk.service;

import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;

import java.util.Collection;

public interface CityService {

    CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
    static boolean checkCityByName(String cityName){
        Collection<City> cities = cityDAO.getAll();

        if (cities == null) return false;

        for(City c: cities){
            if (c.getCityName().equals(cityName)) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }

    static boolean checkCityById(int id){
        Collection<City> cities = cityDAO.getAll();
        for(City c: cities){
            if (c.getId() == id) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }
}
