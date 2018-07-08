package com.gerasimchuk.dto;

import com.gerasimchuk.enums.UserRole;

public class UserDTOImpl implements UserDTO {
    private String userName;
    private String middleName;
    private String lastName;
    private String password;
    private UserRole role;

    public UserDTOImpl() {
    }

    public UserDTOImpl(String userName, String middleName, String lastName, String password, UserRole role) {
        this.userName = userName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    @Override
    public UserRole getRole() {
        return role;
    }
    @Override
    public void setRole(UserRole role) {
        this.role = role;
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
}
