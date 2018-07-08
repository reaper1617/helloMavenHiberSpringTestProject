package com.gerasimchuk.dto;

import com.gerasimchuk.enums.UserRole;

public interface UserDTO {
     UserRole getRole();


    void setRole(UserRole role);

    String getUserName();

    void setUserName(String userName);

    String getMiddleName();

    void setMiddleName(String middleName);
    String getLastName();

    void setLastName(String lastName);

    String getPassword() ;

    void setPassword(String password);
}
