package com.gerasimchuk.dto;

import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.OrderState;

public interface DriverStateDTO {
    String getDriverState();

    void setDriverState(String driverState);

    int[] getCargoForChangeState();

    void setCargoForChangeState(int[] cargoForChangeState);

    int getCargoStateChangeTo();

    void setCargoStateChangeTo(int cargoStateChangeTo);
    DriverState getDriverStateVal();

    String getOrderStateChangeTo();

    OrderState getOrderStateVal();
}
