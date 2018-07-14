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

import static com.gerasimchuk.service.UserService.validateName;

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

    @Override
    public boolean changeDriverInDatabase(DriverDTO driverDTO) {
        if (!validateDriverDTOData(driverDTO)) return false;
        User user = userDAO.getById(driverDTO.getDriverIdVal());
        Driver d = user.getDriver();
        String newUserName = user.getUserName();
        String newMiddleName = user.getMiddleName();
        String newLastName = user.getLastName();
        String newPassword = user.getPassword();
        double newHoursWorked = d.getHoursWorked();
        City newCity = d.getCurrentCity();
        Truck newTruck = d.getCurrentTruck();
        if (driverDTO.getUserName() != null) newUserName = driverDTO.getUserName();
        if (driverDTO.getMiddleName() != null) newMiddleName = driverDTO.getMiddleName();
        if (driverDTO.getLastName() != null) newLastName = driverDTO.getLastName();
        if (driverDTO.getPassword() != null) newPassword = driverDTO.getPassword();
        if (driverDTO.getHoursWorked() != null) newHoursWorked = driverDTO.getHouseWorkedVal();
        if (driverDTO.getCurrentCity() != null) newCity = cityDAO.getByName(driverDTO.getCurrentCity());
        if (driverDTO.getCurrentTruck()!= null) newTruck = truckDAO.getById(driverDTO.getCurrentTruckId());
        driverDAO.update(d.getId(),newHoursWorked,d.getState(),newCity, newTruck);
        userDAO.updateDriver(d.getId(),newUserName,newMiddleName,newLastName,newPassword, d);
        return true;
    }


    private boolean validateHoursWorked(double hours){
        if (hours < 0) return false;
        if (hours > Constants.MAX_DRIVER_HOURS_WORKED_IN_MONTH) return false;
        return true;
    }

}
