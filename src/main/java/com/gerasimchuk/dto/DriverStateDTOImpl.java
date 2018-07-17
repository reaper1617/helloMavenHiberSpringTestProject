package com.gerasimchuk.dto;

import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.OrderState;

public class DriverStateDTOImpl implements DriverStateDTO {
    private String driverState;
    private int[] cargoForChangeState;
    private int cargoStateChangeTo;
    private String orderStateChangeTo;

    public DriverStateDTOImpl() {
    }

    public DriverStateDTOImpl(String driverState, int[] cargoForChangeState, int cargoStateChangeTo, String orderStateChangeTo) {
        this.driverState = driverState;
        this.cargoForChangeState = cargoForChangeState;
        this.cargoStateChangeTo = cargoStateChangeTo;
        this.orderStateChangeTo = orderStateChangeTo;
    }

    @Override
    public String getDriverState() {
        return driverState;
    }

    @Override
    public String getOrderStateChangeTo() {
        return orderStateChangeTo;
    }

    @Override
    public OrderState getOrderStateVal(){
        if (orderStateChangeTo.equals("Order executing")) return OrderState.SHIPPED;
        if (orderStateChangeTo.equals("Order executed")) return OrderState.DELIVERED;
        return OrderState.PREPARED; // default
    }

    public void setOrderStateChangeTo(String orderStateChangeTo) {
        this.orderStateChangeTo = orderStateChangeTo;
    }

    @Override
    public DriverState getDriverStateVal(){
        if (driverState.equals("Resting")) return DriverState.RESTING;
        if (driverState.equals("Driving")) return DriverState.DRIVING;
        if (driverState.equals("Second driver")) return DriverState.SECOND_DRIVER;
        if (driverState.equals("Loading/unloading cargos")) return DriverState.LOAD_UNLOAD_WORKS;
        if (driverState.equals("Free")) return DriverState.FREE;
        return DriverState.FREE; // default
    }

    @Override
    public void setDriverState(String driverState) {
        this.driverState = driverState;
    }

    @Override
    public int[] getCargoForChangeState() {
        return cargoForChangeState;
    }

    @Override
    public void setCargoForChangeState(int[] cargoForChangeState) {
        this.cargoForChangeState = cargoForChangeState;
    }

    @Override
    public int getCargoStateChangeTo() {
        return cargoStateChangeTo;
    }

    @Override
    public void setCargoStateChangeTo(int cargoStateChangeTo) {
        this.cargoStateChangeTo = cargoStateChangeTo;
    }
}
