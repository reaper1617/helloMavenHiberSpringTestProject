package com.gerasimchuk.dto;

public class TruckToOrderDTOimpl implements TruckToOrderDTO {

    private String orderDescription;
    private String truckRegNum;


    public TruckToOrderDTOimpl(String orderDescription, String truckRegNum) {
        this.orderDescription = orderDescription;
        this.truckRegNum = truckRegNum;
    }

    public TruckToOrderDTOimpl() {

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
