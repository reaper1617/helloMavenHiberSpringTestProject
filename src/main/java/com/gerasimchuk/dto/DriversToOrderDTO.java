package com.gerasimchuk.dto;

public interface DriversToOrderDTO {
    int[] getDriverId() ;

     void setDriverId(int[] driverId) ;

    String getOrderDescription() ;

    void setOrderDescription(String orderDescription) ;

    String getTruckRegNum() ;

    void setTruckRegNum(String truckRegNum);
}
