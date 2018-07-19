package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.CargoDAO;
import com.gerasimchuk.dao.CargoDAOImpl;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.RoutePointType;

import java.util.Collection;

public interface CargoService {




    boolean validateCargoDTOData(CargoDTO cargoDTO);

    boolean addCargoToDatabase(CargoDTO cargoDTO);

    boolean changeCargoInDatabase(CargoDTO cargoDTO);

    Collection<Cargo> getCargosInCityByOrder(User d);

    boolean validateCargoName(String name);

    static boolean validateCargoWeight(double weight){
        if (weight <= 0) return false;
        if (weight > Constants.MAX_CARGO_WEIGHT) return false;
        return true;
    }

    static boolean validateCargoStatus(String status){
        if (status == null) return false;
        if (status.equals("Prepared") || status.equals("Shipped") || status.equals("Delivered")) return true;
        return false;
    }

    boolean validateRoutePoint(String routepoint, RoutePointType type);

    boolean validateRoutePointId(int id);

    boolean validateRoutePoints(String routepointFrom, String routepointTo);
    boolean validateRoutePointsByIDs(int routepointIdFrom, int routepointIdTo);

    Collection<Cargo> getCargosWithoutAssignedOrder();
}
