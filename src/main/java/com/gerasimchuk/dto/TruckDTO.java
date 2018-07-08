package com.gerasimchuk.dto;

import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;

public interface TruckDTO {

    public String getRegistrationNumber();

    public void setRegistrationNumber(String registrationNumber);

    public String getShift() ;

    public int getShiftVal();
    public void setShift(String shift);

    public String getCapacity() ;

    public double getCapacityVal();

    public void setCapacity(String capacity);

    public String getState() ;

    public TruckState getStateVal();

    public void setState(String state) ;

    public String getCurrentCity() ;

    public void setCurrentCity(String currentCity) ;
}
