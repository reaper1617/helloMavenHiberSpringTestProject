package com.gerasimchuk.service;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.AdminDTO;
import com.gerasimchuk.dto.AdminDTOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/config/application-context.xml")
class AdminServiceImplTest  {

    private UserDAO userDAO = new UserDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private static CityDAO cityDAO = new CityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private TruckDAO truckDAO = new TruckDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private DriverDAO driverDAO = new DriverDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private OrderDAO orderDAO = new OrderDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private AdminService adminService = new AdminServiceImpl(userDAO,truckDAO,driverDAO, orderDAO);

    public AdminServiceImplTest() {
    }

    @Test
    public void deleteTruckFromDatabase() {
        City city = cityDAO.create("TestCity",CityHasAgency.HAS);
        Truck truck = truckDAO.create("ss44444",1,2,TruckState.NOTREADY,city);
        AdminDTO adminDTO = new AdminDTOImpl(0,0,0,0,0,truck.getId(),0,0,0);
        boolean success = adminService.deleteTruckFromDatabase(adminDTO);
        Truck deletedTruck = truckDAO.getByRegistrationNumber("ss44444");
        assertNull(deletedTruck);
        assertEquals(success,true);
    }
}