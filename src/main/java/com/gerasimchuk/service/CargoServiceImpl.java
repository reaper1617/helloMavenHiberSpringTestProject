package com.gerasimchuk.service;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.RoutePointType;

import java.util.Collection;

public class CargoServiceImpl implements CargoService {
    private static CargoDAO cargoDAO = CargoDAOImpl.getCargoDAOInstance();
    private static CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
    private static RoutepointDAO routepointDAO = RoutepointDAOImpl.getRoutepointDAOInstance();

    @Override
    public boolean validateCargoDTOData(CargoDTO cargoDTO) {
        if (cargoDTO == null) return false;
        // validate name
        if (!CargoService.validateCargoName(cargoDTO.getName())) return false;
        // validate weight
        if (!CargoService.validateCargoWeight(cargoDTO.getWeightVal())) return false;

        //validate status
        if (!CargoService.validateCargoStatus(cargoDTO.getStatus())) return false;
        // validate routepoints
        if (!validateRoutePoints(cargoDTO.getLoadPoint(), cargoDTO.getUnloadPoint())) return false;
        return true;
    }

    @Override
    public boolean addCargoToDatabase(CargoDTO cargoDTO) {
        if (!validateCargoDTOData(cargoDTO)) return false;
        RoutePoint from = routepointDAO.getByNameAndType(cargoDTO.getLoadPoint(), RoutePointType.LOADING);
        RoutePoint to = routepointDAO.getByNameAndType(cargoDTO.getUnloadPoint(), RoutePointType.UNLOADING);
        if (from == null || to == null) return false;
        cargoDAO.create(cargoDTO.getName(),cargoDTO.getWeightVal(),cargoDTO.getStatusVal(),from, to);
        return true;
    }

    @Override
    public boolean validateRoutePoint(String routepoint, RoutePointType type) {
        if (routepoint == null) return false;
        Collection<RoutePoint> routePoints = routepointDAO.getAll();
        if (routePoints!=null){
            for(RoutePoint routePoint : routePoints){
                if (routePoint.getCity().getCityName().equals(routepoint) && routePoint.getType() == type) return true;
            }
        }
        // if there is no routepoint , but there is a basic city entity instance
        Collection<City> cities = cityDAO.getAll();
        City city = null;
        if (cities != null){
            for(City c : cities){
                if (c.getCityName().equals(routepoint)) city = c;
            }
        }
        if (city!=null){
            routepointDAO.create(RoutePointType.LOADING, city);
            routepointDAO.create(RoutePointType.UNLOADING, city);
            return true;
        }

        return false;
    }

    @Override
    public boolean validateRoutePoints(String routepointFrom, String routepointTo) {
        if (!validateRoutePoint(routepointFrom, RoutePointType.LOADING)) return false;
        if (!validateRoutePoint(routepointTo, RoutePointType.UNLOADING)) return false;
        if (routepointFrom.equals(routepointTo)) return false;
        return true;
    }
}
