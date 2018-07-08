package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.dao.TruckDAO;
import com.gerasimchuk.dao.TruckDAOImpl;
import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.enums.TruckState;

import java.util.Collection;

public class TruckServiceImpl implements TruckService {


    private static TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();

    private static CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();

    @Override
    public boolean validateTruckDTOData(TruckDTO truckDTO) {

        // check registration num
        if (!validateRegistrationNumber(truckDTO.getRegistrationNumber())) return false;

        // check shift
        if (!checkShift(truckDTO.getShiftVal())) return false;

        // check capacity
        if (!checkCapacity(truckDTO.getCapacityVal())) return false;

        // check state
        if (!checkState(truckDTO.getStateVal())) return false;

        // check city
        if (!checkCity(truckDTO.getCurrentCity())) return false;

        return true;
    }

    private boolean validateRegistrationNumber(String regNum){
        if (regNum == null) return false;
        if (regNum.length()!=7) return false;

        if ( !(Character.isLetter(regNum.charAt(0)) && Character.isLetter(regNum.charAt(1)))) return false;
        for(int i = 2; i < 7; i++){
            if (!(Character.isDigit(regNum.charAt(i)))) return false;
        }
        return true;
    }

    private boolean checkShift(int shift){
        if (shift<=0) return false;
        if (shift > Constants.MAX_TRUCK_SHIFT_SIZE) return false;
        return true;
    }

    private boolean checkCapacity(double capacity){
        if (capacity<=0) return false;
        if (capacity> Constants.MAX_TRUCK_CAPACITY_SIZE) return false;
        return true;
    }

    private boolean checkState(TruckState state){
        if (state == null) return false;
        return true;
    }

    private boolean checkCity(String city){
        Collection<City> cities = cityDAO.getAll();

        if (cities == null) return false;

        for(City c: cities){
            if (c.getCityName().equals(city)) {
                if (c.getHasAgency() == CityHasAgency.HAS) return true;
            }
        }
        return false;
    }





    @Override
    public boolean addTruckToDatabase(TruckDTO truckDTO) {
        if (!validateTruckDTOData(truckDTO)) return false;

        City city = cityDAO.getByName(truckDTO.getCurrentCity());
        truckDAO.create(truckDTO.getRegistrationNumber(),
                        truckDTO.getShiftVal(),
                        truckDTO.getCapacityVal(),
                        truckDTO.getStateVal(),
                        city);
        return true;
    }
}
