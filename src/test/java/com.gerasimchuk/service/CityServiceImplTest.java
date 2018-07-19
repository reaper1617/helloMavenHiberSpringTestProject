package com.gerasimchuk.service;

import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceImplTest {


    private CityDAO cityDAO = new CityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private CityService cityService = new CityServiceImpl(cityDAO);


    @Test
    void checkCityById() {
        int id = 1;
        City city = cityDAO.getById(id);
        assertNotNull(city);
    }
}