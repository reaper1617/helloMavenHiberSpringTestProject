package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;

import java.util.Collection;

public interface UserDAO {
    User createManager(String userName,
                String middleName,
                String lastname,
                String password,
                Manager manager);

    User createDriver(String userName,
                String middleName,
                String lastname,
                String password,
                Driver driver);

    User updateManager(int id,
                String userName,
                String middleName,
                String lastname,
                String password,
                Manager manager);

    User updateDriver(int id,
                       String userName,
                       String middleName,
                       String lastname,
                       String password,
                       Driver driver);

    User getById(int id);
    Collection<User> getAll();
    void delete(int id);

}
