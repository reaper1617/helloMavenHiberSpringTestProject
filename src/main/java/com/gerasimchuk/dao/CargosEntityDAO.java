package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CargosEntity;

import java.util.Collection;

public interface CargosEntityDAO {

    CargosEntity create(String cargoName);

    CargosEntity update(int id, String cargoName);

    CargosEntity getById(int id);
    Collection<CargosEntity> getAll();
    void delete(int id);
}
