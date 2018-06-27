package com.gerasimchuk.dao;

import com.gerasimchuk.entities.UsersEntity;
import org.hibernate.SessionFactory;

import java.util.Collection;

public interface UsersEntityDAO {

    UsersEntity create(String uname, String lastname, String unumber);
    UsersEntity update(int id, String uname, String lastname, String unumber);
    UsersEntity getById(int id);
    Collection<UsersEntity> getAll();
    void delete(int id);


}
