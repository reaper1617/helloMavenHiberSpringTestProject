package com.gerasimchuk.service;

import com.gerasimchuk.dao.TruckDAO;
import com.gerasimchuk.dao.TruckDAOImpl;
import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Truck;

import java.util.Collection;

public interface TruckService {



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

    boolean validateTruckById(int id);
}
