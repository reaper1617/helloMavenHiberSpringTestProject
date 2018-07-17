package com.gerasimchuk.service;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.RoutePoint;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.enums.RoutePointType;

import java.util.ArrayList;
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
        //if (!validateRoutePoints(cargoDTO.getLoadPoint(), cargoDTO.getUnloadPoint())) return false;
        if (!validateRoutePointsByIDs(cargoDTO.getLoadPointId(),cargoDTO.getUnloadPointId())) return false;
        return true;
    }

    @Override
    public boolean addCargoToDatabase(CargoDTO cargoDTO) {
        if (!validateCargoDTOData(cargoDTO)) return false;
        RoutePoint from = routepointDAO.getByCityId(cargoDTO.getLoadPointId());
        RoutePoint to = routepointDAO.getByCityId(cargoDTO.getUnloadPointId());
        if (from == null || to == null) return false;
        cargoDAO.create(cargoDTO.getName(),cargoDTO.getWeightVal(),cargoDTO.getStatusVal(),from, to);
        return true;
    }

    @Override
    public boolean changeCargoInDatabase(CargoDTO cargoDTO) {
        //if (!validateCargoDTOData(cargoDTO)) return false;
        if (cargoDTO.getName()!=null && cargoDTO.getName().length()!=0)
            if(!CargoService.validateCargoName(cargoDTO.getName()))return false;

        if (cargoDTO.getWeight()!= null && cargoDTO.getWeight().length()!=0)
            if(!CargoService.validateCargoWeight(cargoDTO.getWeightVal())) return false;

        if (cargoDTO.getStatus()!=null && cargoDTO.getStatus().length()!=0)
            if (!CargoService.validateCargoStatus(cargoDTO.getStatus())) return false;

        if (cargoDTO.getLoadPoint()!=null && cargoDTO.getLoadPoint().length()!=0)
            if (!validateRoutePointId(cargoDTO.getLoadPointId()))return false;

        if (cargoDTO.getUnloadPoint()!=null && cargoDTO.getUnloadPoint().length()!=0)
            if (!validateRoutePointId(cargoDTO.getUnloadPointId())) return false;

        Cargo cargo = cargoDAO.getById(cargoDTO.getCargoIdVal());
        if (cargo == null) return false;
        String newName = cargo.getCargoName();
        double newWeight = cargo.getWeight();
        CargoStatus newStatus = cargo.getStatus();
        RoutePoint newLoadPoint = cargo.getLoadPoint();
        RoutePoint newUnloadPoint = cargo.getUnloadPoint();
        Order order = cargo.getAssignedOrder();

        if (cargoDTO.getName()!=null && cargoDTO.getName().length()!=0) newName = cargoDTO.getName();
        if (cargoDTO.getWeight()!=null && cargoDTO.getWeight().length()!=0) newWeight = cargoDTO.getWeightVal();
        if (cargoDTO.getStatus()!= null && cargoDTO.getStatus().length()!=0) newStatus = cargoDTO.getStatusVal();
        if (cargoDTO.getLoadPoint()!=null && cargoDTO.getLoadPoint().length()!=0) newLoadPoint = routepointDAO.getByCityId(cargoDTO.getLoadPointId());
        if (cargoDTO.getUnloadPoint()!=null && cargoDTO.getUnloadPoint().length()!=0) newUnloadPoint = routepointDAO.getByCityId(cargoDTO.getUnloadPointId());

        if (newLoadPoint == newUnloadPoint) return false;

        cargoDAO.update(cargo.getId(),newName,newWeight,newStatus,order, newLoadPoint, newUnloadPoint);
        return true;
    }

    @Override
    public Collection<Cargo> getCargosInCity(City city) {
        if (city == null) return null;
        Collection<Cargo> allCargos = cargoDAO.getAll();
        if (allCargos == null) return null;
        Collection<Cargo> result = new ArrayList<>();
        for(Cargo c: allCargos){
            if (c.getLoadPoint().getCity().getCityName().equals(city.getCityName()) ||
                    c.getUnloadPoint().getCity().getCityName().equals(city.getCityName())) result.add(c);
        }
        return result;
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
    public boolean validateRoutePointId(int id) {
        if (id == 0) return false;
        Collection<RoutePoint> routePoints = routepointDAO.getAll();
        if (routePoints != null){
            for(RoutePoint r : routePoints){
                if (r.getCity().getId() == id) return true;
            }
        }
        // if there is no routepoint but thete is basic city
        // creating new routepoints
        City city = cityDAO.getById(id);
        routepointDAO.create(RoutePointType.LOADING,city);
        routepointDAO.create(RoutePointType.UNLOADING, city);
        return true;
    }

    @Override
    public boolean validateRoutePoints(String routepointFrom, String routepointTo) {
        if (!validateRoutePoint(routepointFrom, RoutePointType.LOADING)) return false;
        if (!validateRoutePoint(routepointTo, RoutePointType.UNLOADING)) return false;
        if (routepointFrom.equals(routepointTo)) return false;
        return true;
    }

    @Override
    public boolean validateRoutePointsByIDs(int routepointIdFrom, int routepointIdTo) {
        if (routepointIdFrom == routepointIdTo) return false;
        if (!validateRoutePointId(routepointIdFrom)) return false;
        if (!validateRoutePointId(routepointIdTo)) return false;
        return true;
    }

    @Override
    public Collection<Cargo> getCargosWithoutAssignedOrder() {
        Collection<Cargo> cargos = cargoDAO.getAll();
        Collection<Cargo> result = new ArrayList<>();
        if (cargos!= null){
            for(Cargo c: cargos){
                if (c.getAssignedOrder() == null) result.add(c);
            }
        }
        return result;
    }
}
