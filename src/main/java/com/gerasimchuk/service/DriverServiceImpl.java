package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.DriverState;

import java.util.Collection;

public class DriverServiceImpl implements DriverService {

    private static UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
    private static DriverDAO driverDAO = DriverDAOImpl.getDriverDAOInstance();
    private static CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
    private static TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();

    @Override
    public boolean validateDriverDTOData(DriverDTO driverDTO) {
        if (driverDTO == null) return false;

        // validate username, middlename, lastname
        if (!validateName(driverDTO.getUserName())) return false;
        if (!validateName(driverDTO.getMiddleName())) return false;
        if (!validateName(driverDTO.getLastName())) return false;

        // validate hoursworked
        if (!validateHoursWorked(driverDTO.getHouseWorkedVal())) return false;

        // validate current city
        if (!CityService.checkCity(driverDTO.getCurrentCity())) return false;

        // validate current truck
        if (!TruckService.validateRegistrationNumber(driverDTO.getCurrentTruck())) return false;

        return true;
    }



    @Override
    public boolean addDriverToDatabase(DriverDTO driverDTO) {
        if (!validateDriverDTOData(driverDTO)) return false;

        Collection<User> users = userDAO.getAll();

        if (users!=null) {

            for (User u : users) {
                if (u.getDriver() != null){

                    if (u.getUserName().equals(driverDTO.getUserName()) &&
                            u.getMiddleName().equals(driverDTO.getMiddleName()) &&
                            u.getLastName().equals(driverDTO.getLastName())) {
                        return false;
                    }
                }
            }
        }

        City city = cityDAO.getByName(driverDTO.getCurrentCity());
        Truck truck = truckDAO.getByRegistrationNumber(driverDTO.getCurrentTruck());


        Driver driver = driverDAO.createDriver(driverDTO.getHouseWorkedVal(),DriverState.RESTING, city,truck);

        userDAO.createDriver(driverDTO.getUserName(),
                            driverDTO.getMiddleName(),
                            driverDTO.getLastName(),
                            driverDTO.getPassword(),
                            driver);
        return true;
    }



    private boolean validateName(String name){
        if (name == null) return false;
        if (name.length() > Constants.MAX_NAME_LENGTH) return false;
        for(int i = 0; i < name.length(); i++){
            if (Character.isDigit(name.charAt(i))) return false;
        }
        return true;
    }

    private boolean validateHoursWorked(double hours){
        if (hours < 0) return false;
        if (hours > Constants.MAX_DRIVER_HOURS_WORKED_IN_MONTH) return false;
        return true;
    }

}
