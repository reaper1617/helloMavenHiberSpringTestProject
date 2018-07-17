package com.gerasimchuk.service;

import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CityServiceImpl implements CityService {

    private CityDAO cityDAO;

    @Autowired
    public CityServiceImpl(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    public boolean checkCityByName(String cityName){
        Collection<City> cities = cityDAO.getAll();

        if (cities == null) return false;

        for(City c: cities){
            if (c.getCityName().equals(cityName)) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkCityById(int id){
        Collection<City> cities = cityDAO.getAll();
        for(City c: cities){
            if (c.getId() == id) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }

}
