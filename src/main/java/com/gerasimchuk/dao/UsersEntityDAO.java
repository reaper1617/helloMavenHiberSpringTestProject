package com.gerasimchuk.dao;

import com.gerasimchuk.entities.UsersEntity;

import java.util.Collection;

public interface UsersEntityDAO {

    UsersEntity create(String userName,
                 String userMiddleName,
                 String userLastname,
                 String userPassword,
                 int userManager,
                 int userDriver);

    UsersEntity update(int id,
                 String userName,
                 String userMiddleName,
                 String userLastname,
                 String userPassword,
                 int userManager,
                 int userDriver);

    UsersEntity getById(int id);
    Collection<UsersEntity> getAll();
    void delete(int id);


}
