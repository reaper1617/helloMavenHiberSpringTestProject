package com.gerasimchuk.dto;

import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;

public class TruckDTOImpl implements TruckDTO {
    private String truckId;
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

    public TruckDTOImpl(String truckId, String registrationNumber, String shift, String capacity, String state, String currentCity) {
        this.truckId = truckId;
        this.registrationNumber = registrationNumber;
        this.shift = shift;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
    }

    @Override
    public String getTruckId() {
        return truckId;
    }

    public int getTruckIdVal(){
        int id = 0;
        try{
            id = Integer.parseInt(truckId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void setTruckId(String truckId) {
        this.truckId = truckId;
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
    public int getCurrentCityId() {
        int id = 0;
        try{
            id = Integer.parseInt(currentCity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}
