package com.gerasimchuk.dao;

import com.gerasimchuk.entities.CitiesEntity;
import com.gerasimchuk.entities.MapEntity;
import com.gerasimchuk.entities.MapEntityPK;

import java.util.Collection;

public interface MapEntityDAO {
    MapEntity create(CitiesEntity departureCity,
                    CitiesEntity destinationCity,
                    double distance);

    MapEntity update(MapEntityPK id,
                     CitiesEntity departureCity,
                     CitiesEntity destinationCity,
                     double distance);

    MapEntity getById(MapEntityPK id);
    Collection<MapEntity> getAll();
    void delete(MapEntityPK id);
}
