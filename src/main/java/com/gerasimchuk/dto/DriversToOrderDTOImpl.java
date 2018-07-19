package com.gerasimchuk.dto;

public class DriversToOrderDTOImpl implements DriversToOrderDTO {

    private int[] driverId;
    private String orderDescription;
    private String truckRegNum;

    public DriversToOrderDTOImpl() {
    }

    public DriversToOrderDTOImpl(int[] driverId, String orderDescription, String truckRegNum) {
        this.driverId = driverId;
        this.orderDescription = orderDescription;
        this.truckRegNum = truckRegNum;
    }

    @Override
    public int[] getDriverId() {
        return driverId;
    }

    @Override
    public void setDriverId(int[] driverId) {
        this.driverId = driverId;
    }

    @Override
    public String getOrderDescription() {
        return orderDescription;
    }

    @Override
    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    @Override
    public String getTruckRegNum() {
        return truckRegNum;
    }

    @Override
    public void setTruckRegNum(String truckRegNum) {
        this.truckRegNum = truckRegNum;
    }
}
