package com.gerasimchuk.dao;

import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.enums.ManagerPosition;

import java.util.Collection;

public interface ManagerDAO {
    Manager create(ManagerPosition managerPosition);

    Manager update(int id,
                   double hoursWorked,
                   ManagerPosition managerPosition);

    Manager getById(int id);
    Collection<Manager> getAll();
    void delete(int id);
}
