package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.CityDAO;
import com.gerasimchuk.dao.CityDAOImpl;
import com.gerasimchuk.dao.TruckDAO;
import com.gerasimchuk.dao.TruckDAOImpl;
import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;

public class TruckServiceImpl implements TruckService {


    private static TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();

    private static CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();



    @Override
    public boolean validateTruckDTOData(TruckDTO truckDTO) {

        if (truckDTO == null) return false;

        // check registration num
        if (!TruckService.validateRegistrationNumber(truckDTO.getRegistrationNumber())) return false;

        // check shift
        if (!checkShift(truckDTO.getShiftVal())) return false;

        // check capacity
        if (!checkCapacity(truckDTO.getCapacityVal())) return false;

        // check state
        if (!checkState(truckDTO.getStateVal())) return false;

        // check city
        if (!CityService.checkCityByName(truckDTO.getCurrentCity())) return false;

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


    @Override
    public boolean addTruckToDatabase(TruckDTO truckDTO) {
        if (!validateTruckDTOData(truckDTO)) return false;

        City city = cityDAO.getByName(truckDTO.getCurrentCity());

        if (truckDAO.getByRegistrationNumber(truckDTO.getRegistrationNumber())!=null) return false;

        truckDAO.create(truckDTO.getRegistrationNumber(),
                        truckDTO.getShiftVal(),
                        truckDTO.getCapacityVal(),
                        truckDTO.getStateVal(),
                        city);
        return true;
    }

    @Override
    public boolean changeTruckInDatabase(TruckDTO truckDTO) {
        if (truckDTO == null) return false;
        // check shift
        if (!checkShift(truckDTO.getShiftVal())) return false;
        // check capacity
        if (!checkCapacity(truckDTO.getCapacityVal())) return false;
        // check state
        if (!checkState(truckDTO.getStateVal())) return false;
        // check city
        if (!CityService.checkCityByName(truckDTO.getCurrentCity())) return false;


        Truck t =truckDAO.getByRegistrationNumber(truckDTO.getRegistrationNumber());

        double newCapacity = t.getCapacity();
        City newCity = t.getCurrentCity();
        TruckState newState = t.getState();
        int newShift = t.getShift();

        if (truckDTO.getCapacity()!=null) newCapacity = truckDTO.getCapacityVal();
        if (truckDTO.getCurrentCity()!=null) newCity = cityDAO.getByName(truckDTO.getCurrentCity());
        if (truckDTO.getState()!=null) newState = truckDTO.getStateVal();
        if (truckDTO.getShift()!=null) newShift = truckDTO.getShiftVal();

        truckDAO.update(t.getId(), t.getRegistrationNumber(), newShift, newCapacity, newState, newCity);
        return true;
    }
}
