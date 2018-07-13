package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.TruckToOrderDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.enums.TruckState;

import java.sql.Timestamp;
import java.util.*;

public class OrderServiceImpl implements OrderService{

    private static TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();
    private static CargoDAO cargoDAO = CargoDAOImpl.getCargoDAOInstance();
    private static RouteDAO routeDAO = RouteDAOImpl.getRouteDAOInstance();
    private static OrderDAO orderDAO = OrderDAOImpl.getOrderDAOInstance();

    @Override
    public boolean validateOrderDTO(OrderDTO orderDTO) {
        // validate description
        if (!validateDescription(orderDTO.getDescription())) return false;

        // validate date

        if (!OrderService.validateDate(orderDTO.getDate())) return false;
        // validate assigned truck

        if (!validateAssignedTruck(orderDTO)) return false;

        // validate cargos[]
        if (!validateCargos(orderDTO.getCargos())) return false;

    return true;
    }

    @Override
    public boolean addOrderToDatabase(OrderDTO orderDTO) {
        if (!validateOrderDTO(orderDTO)) return false;
        Truck t = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck());
        orderDAO.create(OrderState.PREPARED,orderDTO.getDescription(),orderDTO.getDateVal(), t);
        return true;
    }

    @Override
    public boolean addTruckToOrder(TruckToOrderDTO truckToOrderDTO) {

        if (!TruckService.validateRegistrationNumber(truckToOrderDTO.getTruckRegNum())) return false;

        Truck t = truckDAO.getByRegistrationNumber(truckToOrderDTO.getTruckRegNum());

        if (t == null)return false;

        if (!validateDescription(truckToOrderDTO.getOrderDescription())) return false;
        Order order = orderDAO.getByDescription(truckToOrderDTO.getOrderDescription());

        if (order == null) return false;

        orderDAO.update(order.getOrderId(),
                        order.getOrderState(),
                        order.getOrderDescription(),
                        order.getOrderDate(),
                        t);

        return true;
    }


    private static boolean validateDescription(String descriprion){
        if (descriprion == null) return false;
        if (descriprion.length() == 0) return false;
        if (descriprion.length() > Constants.MAX_ORDER_DESCRIPTION_LENGTH) return false;
        return true;
    }



    private boolean validateAssignedTruck(OrderDTO orderDTO){
        if (orderDTO.getAssignedTruck() != null) {
            if (!TruckService.validateRegistrationNumber(orderDTO.getAssignedTruck())) return false;
            Truck t = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck());
            if (t == null) return false;
            if (t.getState() != TruckState.READY) return false;
            // check cargos max weight
            double cargosWeight = 0;
            Collection<Cargo> cargos = cargoDAO.getAll();
            if (cargos != null) {
                for (Cargo c : cargos) {
                    cargosWeight += c.getWeight();
                }
            }
            if (cargosWeight <= t.getCapacity()) return true;
            // clever check!!
            List<City> route = makeOrderRoute(orderDTO);
            Map<City, Double> weights = getCitiesWithWeightsOfAddingCargos(orderDTO);
            double maxweight = 0;
            double currentweight = 0;

            for (City c : route) {
                currentweight += weights.get(c);
                if (currentweight > maxweight) maxweight = currentweight;
            }
            if (maxweight > t.getCapacity()) return false;
        }
        return true;
    }


    private boolean validateCargos(int[] cargosId){
        if (cargosId == null) return false;
        if (cargosId.length == 0) return false;

        Collection<Cargo> cargos = cargoDAO.getAll();
        if (cargos == null) return false;
        for(int i = 0; i < cargosId.length; i++){
            Cargo c = cargoDAO.getById(cargosId[i]);
            if (c == null) return false;
        }
        return true;
    }


    private Map<City, Double> getCitiesWithWeightsOfAddingCargos(OrderDTO orderDTO){
        int[] cargoIDs = orderDTO.getCargos();
        if (cargoIDs == null ) return null;
        if (cargoIDs.length == 0) return null;
        Map<City, Double> cities = new HashMap<>();
        // get map with cities and weight changes
        for(int i = 0; i < cargoIDs.length; i++){
            Cargo c = cargoDAO.getById(cargoIDs[i]);
            if (cities.containsKey(c.getLoadPoint().getCity())) {
                double currentWeight = cities.get(c.getLoadPoint().getCity());
                cities.put(c.getLoadPoint().getCity(),currentWeight + c.getWeight());
            }
            else {
                cities.put(c.getLoadPoint().getCity(),c.getWeight());
            }
            if (cities.containsKey(c.getUnloadPoint().getCity())) {
                double currentWeight = cities.get(c.getUnloadPoint().getCity());
                cities.put(c.getUnloadPoint().getCity(),currentWeight - c.getWeight());
            }
            else {
                cities.put(c.getUnloadPoint().getCity(), (-1)*c.getWeight());
            }
        }
        return cities;
    }

    @Override
    public List<City> makeOrderRoute(OrderDTO orderDTO){
        List<City> citiesList = new ArrayList<>();
        citiesList.addAll(getCitiesWithWeightsOfAddingCargos(orderDTO).keySet());
        City startPoint = null;
        if (orderDTO.getAssignedTruck() != null) {
            startPoint = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck()).getCurrentCity();
        }


        if (startPoint != null) {
            if (!citiesList.contains(startPoint)) {
                // chosen truck is in another city
                // need to add it into list and make new distances matrix
                citiesList.add(startPoint);
            }
        }
        double[][] distances = new double[citiesList.size()][citiesList.size()];
        for(int i = 0; i < distances.length; i++){
            for(int j = 0; j < distances[i].length; j++){
                if (i == j){
                    distances[i][j] = 0;
                }
                else {
                    City cityI = citiesList.get(i);
                    City cityJ = citiesList.get(j);
                    Route r = routeDAO.getByCities(cityI, cityJ);
                    if (r == null) return null;
                    distances[i][j] = r.getDistance();
                }
            }
        }
        //
        int currentIndex;
        if (startPoint!= null) currentIndex = citiesList.indexOf(startPoint);
        else currentIndex = 0;

        Set<Integer> excludedIndexes = new HashSet<>();
        excludedIndexes.add(currentIndex);
        // list of cities in a route
        List<City> finalRoute = new ArrayList<>();
        // count cities with smallest distance between them


        while(excludedIndexes.size()!=citiesList.size()) {
            finalRoute.add(citiesList.get(currentIndex));
            currentIndex = findMinDistanceElementIndex(distances[currentIndex], excludedIndexes);
            excludedIndexes.add(currentIndex);
        }
        finalRoute.add(citiesList.get(currentIndex));
        return finalRoute;
    }

//    private List<City> makeOrderRoute(OrderDTO orderDTO){
//        double[][] distances = makeMatrixOfDistances(orderDTO);
//        if (orderDTO.getAssignedTruck() == null) return null;
//        City startPoint = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck()).getCurrentCity();
//
//    }

    private int findMinDistanceElementIndex(double[] distances, Set<Integer> excludedIndexes){
        double min =   Integer.MAX_VALUE ;//distances[0] == 0 ? distances[1] : distances[0] ;
        int minIndex = 0;
        int length = distances.length;
        for(int i = 0; i < length; i++){
            if (!excludedIndexes.contains(i)) {
                if (distances[i] != 0) {
                    if (distances[i] < min) {
                        min = distances[i];
                        minIndex = i;
                    }
                }
            }
        }
        return minIndex;
    }

}
