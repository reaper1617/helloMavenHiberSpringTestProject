package com.gerasimchuk.service;

import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.DriverDTOImpl;

public interface DriverService {

    boolean validateDriverDTOData(DriverDTO driverDTO);

    boolean addDriverToDatabase(DriverDTO driverDTO);

}
