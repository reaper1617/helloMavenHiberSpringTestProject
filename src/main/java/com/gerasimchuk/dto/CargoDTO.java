package com.gerasimchuk.dto;

import com.gerasimchuk.enums.CargoStatus;

public interface CargoDTO {

     String getName();

     void setName(String name);

     String getWeight() ;

     double getWeightVal();

     void setWeight(String weight);

     String getStatus() ;

     CargoStatus getStatusVal();

     void setStatus(String status);

     String getLoadPoint() ;

     void setLoadPoint(String loadPoint);

     String getUnloadPoint() ;

     void setUnloadPoint(String unloadPoint) ;

     int getLoadPointId();

     int getUnloadPointId();


     String getCargoId();

     void setCargoId(String cargoId);
     int getCargoIdVal();
}
