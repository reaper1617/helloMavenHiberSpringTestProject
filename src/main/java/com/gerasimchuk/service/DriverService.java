package com.gerasimchuk.service;

import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.DriverDTOImpl;
import com.gerasimchuk.dto.DriverStateDTO;
import com.gerasimchuk.entities.Driver;

public interface DriverService {

    boolean validateDriverDTOData(DriverDTO driverDTO);

    boolean addDriverToDatabase(DriverDTO driverDTO);

    boolean changeDriverInDatabase(DriverDTO driverDTO);

    boolean updateDriverState(DriverStateDTO driverStateDTO, Driver currentDriver);

}
