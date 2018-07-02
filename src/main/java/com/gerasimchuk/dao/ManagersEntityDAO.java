package com.gerasimchuk.dao;

import com.gerasimchuk.enums.ManagerPosition;
import com.gerasimchuk.entities.ManagersEntity;

import java.util.Collection;

public interface ManagersEntityDAO {
    ManagersEntity create(ManagerPosition managerPosition);

    ManagersEntity update(int id,
                          ManagerPosition managerPosition);

    ManagersEntity getById(int id);
    Collection<ManagersEntity> getAll();
    void delete(int id);
}
