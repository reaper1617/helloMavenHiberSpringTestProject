package com.gerasimchuk.dto;

public class AdminDTOImpl implements AdminDTO {

    private int userId;
    private int driverId;
    private int managerId;
    private int orderId;
    private int cargoId;
    private int truckId;
    private int cityId;
    private int routepointId;
    private int routeId;

    public AdminDTOImpl() {
    }

    public AdminDTOImpl(int userId, int driverId, int managerId, int orderId, int cargoId, int truckId, int cityId, int routepointId, int routeId) {
        this.userId = userId;
        this.driverId = driverId;
        this.managerId = managerId;
        this.orderId = orderId;
        this.cargoId = cargoId;
        this.truckId = truckId;
        this.cityId = cityId;
        this.routepointId = routepointId;
        this.routeId = routeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getRoutepointId() {
        return routepointId;
    }

    public void setRoutepointId(int routepointId) {
        this.routepointId = routepointId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
