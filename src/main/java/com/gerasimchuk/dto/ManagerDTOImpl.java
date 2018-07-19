package com.gerasimchuk.dto;

import com.gerasimchuk.enums.ManagerPosition;

public class ManagerDTOImpl implements ManagerDTO {

    private String userName;
    private String middleName;
    private String lastName;
    private String password;
    private String managerPosition;

    public ManagerDTOImpl() {
    }

    public ManagerDTOImpl(String userName, String middleName, String lastName, String password, String managerPosition) {
        this.userName = userName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.managerPosition = managerPosition;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getManagerPosition() {
        return managerPosition;
    }

    @Override
    public ManagerPosition getManagerPositionVal(){
        if (managerPosition.equals("Junior")) return ManagerPosition.JUNIOR;
        if (managerPosition.equals("Middle")) return ManagerPosition.MIDDLE;
        if (managerPosition.equals("Expert")) return ManagerPosition.EXPERT;
        return ManagerPosition.JUNIOR;
    }


    @Override
    public void setManagerPosition(String managerPosition) {
        this.managerPosition = managerPosition;
    }
}
