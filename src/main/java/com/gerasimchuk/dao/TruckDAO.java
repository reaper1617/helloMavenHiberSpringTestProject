package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;

import java.util.Collection;

public interface TruckDAO {
    Truck create(String registrationNumber,
                int shift,
                double capacity,
                TruckState state,
                City currentCity);

    Truck update(int id,
                 String registrationNumber,
                 int shift,
                 double capacity,
                 TruckState state,
                 City currentCity);

    Truck getById(int id);
    Truck getByRegistrationNumber(String regNum);


    Collection<Truck> getAll();
    void delete(int id);
}
