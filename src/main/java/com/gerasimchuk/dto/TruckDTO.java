package com.gerasimchuk.dto;

import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;

public interface TruckDTO {

     String getTruckId();

     int getTruckIdVal();

     void setTruckId(String truckId);

     String getRegistrationNumber();

     void setRegistrationNumber(String registrationNumber);

     String getShift() ;

     int getShiftVal();
     void setShift(String shift);

     String getCapacity() ;

     double getCapacityVal();

    void setCapacity(String capacity);

     String getState() ;

     TruckState getStateVal();

     void setState(String state) ;

     String getCurrentCity() ;

     void setCurrentCity(String currentCity) ;
}
