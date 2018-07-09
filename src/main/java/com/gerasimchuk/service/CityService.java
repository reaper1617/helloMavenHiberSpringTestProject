package com.gerasimchuk.service;

import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;

import java.util.Collection;

public interface CityService {

    CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
    static boolean checkCity(String city){
        Collection<City> cities = cityDAO.getAll();

        if (cities == null) return false;

        for(City c: cities){
            if (c.getCityName().equals(city)) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }

}
