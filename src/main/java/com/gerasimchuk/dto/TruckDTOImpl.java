package com.gerasimchuk.dto;

import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;

public class TruckDTOImpl implements TruckDTO {
    private String registrationNumber;
    private String shift;
    private String capacity;
    private String state;
    private String currentCity;

    public TruckDTOImpl() {
    }

    public TruckDTOImpl(String registrationNumberVal, String shiftVal, String capacityVal, String stateVal, String currentCityVal) {
        this.registrationNumber = registrationNumberVal;
        this.shift = shiftVal;
        this.capacity = capacityVal;
        this.state = stateVal;
        this.currentCity = currentCityVal;
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String getShift() {
        return shift;
    }

    @Override
    public int getShiftVal(){
        return Integer.parseInt(shift);
    }

    @Override
    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getCapacity() {
        return capacity;
    }

    @Override
    public double getCapacityVal(){
        return Double.parseDouble(capacity);
    }

    @Override
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public TruckState getStateVal(){
        if (state == null  ) return null;
        if (state.equals("Ready")) return TruckState.READY;
        if (state.equals("Not ready")) return TruckState.NOTREADY;
        return TruckState.NOTREADY;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getCurrentCity() {
        return currentCity;
    }

    @Override
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}
