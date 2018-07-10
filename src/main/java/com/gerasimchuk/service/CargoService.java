package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.CargoDAO;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.enums.RoutePointType;

public interface CargoService {


    boolean validateCargoDTOData(CargoDTO cargoDTO);

    boolean addCargoToDatabase(CargoDTO cargoDTO);

    static boolean validateCargoName(String name){
        if (name == null) return false;
        if (name.length() > Constants.MAX_CARGO_NAME_LENGTH) return false;
        for(int i = 0; i < name.length(); i++){
            if (Character.isDigit(name.charAt(i))) return false;
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

    boolean validateRoutePoints(String routepointFrom, String routepointTo);

}
