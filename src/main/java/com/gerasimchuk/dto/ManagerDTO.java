package com.gerasimchuk.dto;

import com.gerasimchuk.enums.ManagerPosition;

public interface ManagerDTO {
     String getUserName();

     void setUserName(String userName);

     String getMiddleName() ;

     void setMiddleName(String middleName) ;

     String getLastName() ;

     void setLastName(String lastName);

     String getPassword() ;

     void setPassword(String password);

     String getManagerPosition() ;

    void setManagerPosition(String managerPosition) ;

     ManagerPosition getManagerPositionVal();
}
