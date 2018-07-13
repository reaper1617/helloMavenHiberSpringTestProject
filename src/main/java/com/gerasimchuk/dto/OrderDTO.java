package com.gerasimchuk.dto;

import java.sql.Timestamp;
import java.util.Map;

public interface OrderDTO {

    String getDescription() ;

    void setDescription(String description);

    String getDate();

    Timestamp getDateVal();

    void setDate(String date);

    String getAssignedTruck() ;

    void setAssignedTruck(String assignedTruck);

    int[] getCargos() ;

    void setCargos(int[] cargos) ;

}
