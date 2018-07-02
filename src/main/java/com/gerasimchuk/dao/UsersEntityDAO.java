package com.gerasimchuk.dao;

import com.gerasimchuk.entities.DriversEntity;
import com.gerasimchuk.entities.ManagersEntity;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.entities.UsersEntity;

import java.util.Collection;

public interface UsersEntityDAO {


    UsersEntity create(String userName,
                       String userMiddleName,
                       String userLastname,
                       String userPassword,
                       UserRole role,
                       ManagersEntity userManager,
                       DriversEntity userDriver);

    UsersEntity update(int id,
                 String userName,
                 String userMiddleName,
                 String userLastname,
                 String userPassword,
                 UserRole role,
                 ManagersEntity userManager,
                 DriversEntity userDriver);

    UsersEntity getById(int id);
    Collection<UsersEntity> getAll();
    void delete(int id);


}
