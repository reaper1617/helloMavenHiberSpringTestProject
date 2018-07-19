package com.gerasimchuk.service;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.DriverDTOImpl;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceImplTest {

    private UserDAO userDAO = new UserDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private static CityDAO cityDAO = new CityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private TruckDAO truckDAO = new TruckDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private DriverDAO driverDAO = new DriverDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private CityService cityService = new CityServiceImpl(cityDAO);
    private TruckService truckService = new TruckServiceImpl(truckDAO,cityDAO,cityService);
    private DriverService driverService = new DriverServiceImpl(userDAO,driverDAO,cityDAO,truckDAO,cityService,truckService);

    @AfterEach
    void tearDown() {
        Collection<User> users = userDAO.getAll();
        if (users!=null){
            for(User u: users){
                if (u.getUserName().equals("UserName") &&
                        u.getMiddleName().equals("MiddleName") &&
                        u.getLastName().equals("LastName") &&
                        u.getPassword().equals("Password")){
                    int id = u.getDriver().getId();
                    userDAO.delete(u.getId());
                    driverDAO.delete(id);
                    break;
                }
            }
        }
        Truck truck = truckDAO.getByRegistrationNumber("rr33333");
        if (truck!=null) truckDAO.delete(truck.getId());
        City city = cityDAO.getByName("TestCity");
        if (city!=null) cityDAO.delete(city.getId());

    }

    @Test
    void addDriverToDatabase() {
        City city = cityDAO.create("TestCity",CityHasAgency.HAS);
        Truck truck = truckDAO.create("rr33333",2,3,TruckState.NOTREADY, city);
        Integer truckId = truck.getId();
        DriverDTO driverDTO = new DriverDTOImpl("UserName","MiddleName","LastName","Password","10", "1", truckId.toString());
        boolean success = driverService.addDriverToDatabase(driverDTO);
        Collection<User> drivers = userDAO.getAll();
        boolean driverAdded = false;
        for(User u: drivers){
            if (u.getDriver() != null) {
                if (u.getUserName().equals(driverDTO.getUserName()) &&
                        u.getMiddleName().equals(driverDTO.getMiddleName()) &&
                        u.getLastName().equals(driverDTO.getLastName()) &&
                        u.getPassword().equals(driverDTO.getPassword()) &&
                        u.getDriver().getHoursWorked() == driverDTO.getHouseWorkedVal() &&
                        u.getDriver().getCurrentCity().getId() == driverDTO.getCurrentCityId()) {
                            if (u.getDriver().getCurrentTruck()!=null){
                                if (u.getDriver().getCurrentTruck().getId() == driverDTO.getCurrentTruckId()){
                                    driverAdded = true;
                                    break;
                                }
                            }
                }
            }
        }
        assertEquals(true,success);
        assertEquals(true, driverAdded);
    }
}