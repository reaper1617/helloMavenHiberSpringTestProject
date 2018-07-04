package com.gerasimchuk.dao;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;

import java.util.Collection;

public interface RouteDAO {


    Route create(City departureCity, City destinationCity, double distance);

    Route update(int id, City departureCity, City destinationCity, double distance);

    Route getById(int id);
    Collection<Route> getAll();
    void delete(int id);
}
