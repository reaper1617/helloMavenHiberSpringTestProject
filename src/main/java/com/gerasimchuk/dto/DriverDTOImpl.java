package com.gerasimchuk.dto;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverState;

public class DriverDTOImpl implements DriverDTO {
    private String driverId;
    private String userName;
    private String middleName;
    private String lastName;
    private String password;
    private String hoursWorked;
    private String currentCity;
    private String currentTruck;


    public DriverDTOImpl() {
    }

    public DriverDTOImpl(String userName, String middleName, String lastName, String password, String hoursWorked, String currentCity, String currentTruck) {
        this.userName = userName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.hoursWorked = hoursWorked;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }



    @Override
    public String getDriverId() {
        return driverId;
    }

    @Override
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public int getDriverIdVal(){
        int id = 0;
        try{
            id = Integer.parseInt(driverId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public double getHouseWorkedVal(){
        double res = 0;
        try{
            res =  Double.parseDouble(hoursWorked);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getCurrentCity() {
        return currentCity;
    }

    @Override
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public String getCurrentTruck() {
        return currentTruck;
    }

    @Override
    public int getCurrentTruckId(){
        int id =0;
        try {
            id = Integer.parseInt(currentTruck);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getCurrentCityId() {
        int id = 0;
        try {
            id = Integer.parseInt(currentCity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void setCurrentTruck(String currentTruck) {
        this.currentTruck = currentTruck;
    }
}
