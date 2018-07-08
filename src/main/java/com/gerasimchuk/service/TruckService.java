package com.gerasimchuk.service;

import com.gerasimchuk.dto.TruckDTO;

public interface TruckService {

    boolean validateTruckDTOData(TruckDTO truckDTO);

    boolean addTruckToDatabase(TruckDTO truckDTO);
}
