package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.CargoDAO;
import com.gerasimchuk.dao.CargoDAOImpl;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.enums.RoutePointType;

import java.util.Collection;

public interface CargoService {


    boolean validateCargoDTOData(CargoDTO cargoDTO);

    boolean addCargoToDatabase(CargoDTO cargoDTO);

    boolean changeCargoInDatabase(CargoDTO cargoDTO);

    static boolean validateCargoName(String name){
        if (name == null) return false;
        if (name.length() > Constants.MAX_CARGO_NAME_LENGTH) return false;
        for(int i = 0; i < name.length(); i++){
            if (Character.isDigit(name.charAt(i))) return false;
        }
        // check if name unique
        Collection<Cargo> cargos = CargoDAOImpl.getCargoDAOInstance().getAll();
        if (cargos!=null){
            for(Cargo c: cargos){
                if (c.getCargoName().equals(name)) return false;
            }
        }
        return true;
    }

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
}
