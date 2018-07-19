package com.gerasimchuk.service;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.dto.CargoDTOImpl;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CargoServiceImplTest {


    private CargoDAO cargoDAO = new CargoDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private CityDAO cityDAO = new CityDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private RoutepointDAO routepointDAO = new RoutepointDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private CargoService cargoService = new CargoServiceImpl(cargoDAO,cityDAO,routepointDAO);


    @Test
    void addCargoToDatabase() {
        CargoDTO cargoDTO = new CargoDTOImpl("Cargo", "10", "Prepared", "1","2");
        boolean success = cargoService.addCargoToDatabase(cargoDTO);
        List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
        boolean cargoAdded = false;
        for(Cargo c: cargos){
            if (c.getCargoName().equals(cargoDTO.getName()) &&
                    c.getWeight() == cargoDTO.getWeightVal() &&
                    c.getStatus() == cargoDTO.getStatusVal() &&
                    c.getLoadPoint().getCity().getId() == cargoDTO.getLoadPointId() &&
                    c.getUnloadPoint().getCity().getId() == cargoDTO.getUnloadPointId()){
                cargoAdded = true;
                break;
            }
        }
        assertEquals(true, success);
        assertEquals(true, cargoAdded);
    }
}