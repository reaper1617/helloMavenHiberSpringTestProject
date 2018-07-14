package com.gerasimchuk.service;

import com.gerasimchuk.dao.TruckDAO;
import com.gerasimchuk.dao.TruckDAOImpl;
import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Truck;

import java.util.Collection;

public interface TruckService {

    TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();

    boolean validateTruckDTOData(TruckDTO truckDTO);

    boolean addTruckToDatabase(TruckDTO truckDTO);

    boolean changeTruckInDatabase(TruckDTO truckDTO);

    static boolean validateRegistrationNumber(String regNum){
        if (regNum == null) return false;
        if (regNum.length()!=7) return false;

        if ( !(Character.isLetter(regNum.charAt(0)) && Character.isLetter(regNum.charAt(1)))) return false;
        for(int i = 2; i < 7; i++){
            if (!(Character.isDigit(regNum.charAt(i)))) return false;
        }
        return true;
    }

    static boolean validateTruckById(int id){
        if (id <= 0) return false;
        Collection<Truck> trucks = truckDAO.getAll();
        if (trucks == null) return false;
        for(Truck t: trucks){
            if (t.getId() == id) return true;
        }
        return false;
    }
}
