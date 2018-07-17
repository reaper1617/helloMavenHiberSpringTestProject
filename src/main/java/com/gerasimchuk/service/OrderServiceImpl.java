package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.enums.TruckState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{

    private  TruckDAO truckDAO;
    private  CargoDAO cargoDAO;
    private  RouteDAO routeDAO ;
    private  OrderDAO orderDAO ;
    private  UserDAO userDAO;
    private  DriverDAO driverDAO;
    private  UserService userService;

    @Autowired
    public OrderServiceImpl(TruckDAO truckDAO, CargoDAO cargoDAO, RouteDAO routeDAO, OrderDAO orderDAO, UserDAO userDAO, DriverDAO driverDAO, UserService userService) {
        this.truckDAO = truckDAO;
        this.cargoDAO = cargoDAO;
        this.routeDAO = routeDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.driverDAO = driverDAO;
        this.userService = userService;
    }

    @Override
    public boolean validateOrderDTO(OrderDTO orderDTO) {
        // validate description
        if (!validateDescription(orderDTO.getDescription())) return false;

        // validate date

        if (!OrderService.validateDate(orderDTO.getDate())) return false;
        // validate assigned truck

        //if (!validateAssignedTruck(orderDTO)) return false;

        // validate cargos[]
        if (!validateCargos(orderDTO.getCargos())) return false;

    return true;
    }

    @Override
    public boolean addOrderToDatabase(OrderDTO orderDTO) {
        if (!validateOrderDTO(orderDTO)) return false;
        Truck t = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck());
        Order order = orderDAO.create(OrderState.PREPARED,orderDTO.getDescription(),orderDTO.getDateVal(), t);

        int[] cargos = orderDTO.getCargos();
        for(int i = 0; i < cargos.length; i++){
            Cargo c = cargoDAO.getById(cargos[i]);
            cargoDAO.update(c.getId(),c.getCargoName(),c.getWeight(),c.getStatus(),order, c.getLoadPoint(), c.getUnloadPoint());
        }
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



    @Override
    public Collection<Truck> getTrucksFitsToOrder(OrderDTO orderDTO){
        // TODO : check weights!!! it seem to be wrong!!
        Collection<Truck> allTrucks = truckDAO.getAll();
        Collection<Truck> result = new ArrayList<>();
        Map<City, Double> citiesWithWeights = getCitiesWithWeightsOfAddingCargos(orderDTO);
        List<City> route = makeRoute(orderDTO);
        double currentWeightOnRoute = 0;
        double maxWeightOnRoute = 0;
        Collection<Order> orders = orderDAO.getAll();
        for(City c : route){
            currentWeightOnRoute+= citiesWithWeights.get(c);
            if (currentWeightOnRoute > maxWeightOnRoute) maxWeightOnRoute = currentWeightOnRoute;
        }
        if (allTrucks!= null){
            for(Truck t: allTrucks){
                if (t.getCapacity() >= maxWeightOnRoute && t.getState()==TruckState.READY) {
                    boolean isTruckAssignedToOrder = false;
                    for(Order o: orders){
                        if (o.getAssignedTruck() == t) isTruckAssignedToOrder = true;
                    }
                    if (!isTruckAssignedToOrder) result.add(t);
                }
            }
        }
        return result;
    }

//
//    private boolean checkDriver(int id){
//        User user = userDAO.getById(id);
//        if (user == null) return false;
//        return true;
//    }


    @Override
    public Collection<User> getDriversFitToTruckAndOrder(TruckToOrderDTO truckToOrderDTO){
        Truck t = truckDAO.getByRegistrationNumber(truckToOrderDTO.getTruckRegNum());
        if (t == null) return null;

        List<User> allUsers = (ArrayList)userDAO.getAll();

        if (allUsers == null) return null;
        List<User> result = new ArrayList<>();

        for(User u: allUsers){
            Driver d = u.getDriver();
            if (d != null){
               if (d.getCurrentCity() == t.getCurrentCity()){
                   if (d.getState() == DriverState.FREE){
                        if ((d.getHoursWorked() + getTimeOfOrderExecution(truckToOrderDTO) < Constants.MAX_WORK_TIME_LIMIT_FOR_DRIVER_PER_MONTH) &&
                                (!isMonthTransitionWhileOrderExec(truckToOrderDTO))){
                            result.add(u);
                        }
                   }
               }
            }
        }

        return result;
    }


    public double getTimeOfOrderExecution(TruckToOrderDTO truckToOrderDTO){

        // TODO: write real method!!!


        // hours to make order
        return 20;
    }


    public boolean isMonthTransitionWhileOrderExec(TruckToOrderDTO truckToOrderDTO){


        // TODO: make some logic!!

        return false;
    }

    @Override
    public boolean addDriversToOrder(DriversToOrderDTO driversToOrderDTO) {

        Truck t = truckDAO.getByRegistrationNumber(driversToOrderDTO.getTruckRegNum());
        if (t == null) return false;


//        if (!checkDriver(driversToOrderDTO.getDriverIdVal())) return false;
        int[] drivers = driversToOrderDTO.getDriverId();

        if (drivers.length > t.getShift()) return false;

        List<User> users = new ArrayList<>();
        for(int i = 0; i < drivers.length; i++){
            User u = userDAO.getById(drivers[i]);
            if (u == null) return false;
            if (u.getDriver().getCurrentCity() != t.getCurrentCity()) return false;
            if (u.getDriver().getState()!=DriverState.FREE) return false;
            if (u.getDriver().getCurrentTruck()!=null) return false;
            users.add(u);
        }

        Order order =  orderDAO.getByDescription(driversToOrderDTO.getOrderDescription());
        if (order == null) return false;

        for(User u : users){
            Driver d =  u.getDriver();
            driverDAO.update(d.getId(),d.getHoursWorked(),DriverState.RESTING,d.getCurrentCity(),t);
        }
        return true;
    }

    @Override
    public Order findCurrentOrder(Driver d) {

        List<Order> orders = (ArrayList)orderDAO.getAll();
        if (orders == null) return null;
        Truck t = d.getCurrentTruck();
        if (t==null) return null;
        for(Order o: orders){
            if (o.getAssignedTruck()!=null) {
                if (o.getAssignedTruck().getRegistrationNumber().equals(t.getRegistrationNumber())) {
                    return o;
                }
            }
        }
        return null;
    }



//    private boolean validateAssignedTruck(OrderDTO orderDTO){
//        if (orderDTO.getAssignedTruck() != null) {
//            if (!TruckService.validateRegistrationNumber(orderDTO.getAssignedTruck())) return false;
//            Truck t = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck());
//            if (t == null) return false;
//            if (t.getState() != TruckState.READY) return false;
//            // check cargos max weight
//            double cargosWeight = 0;
//            Collection<Cargo> cargos = cargoDAO.getAll();
//            if (cargos != null) {
//                for (Cargo c : cargos) {
//                    cargosWeight += c.getWeight();
//                }
//            }
//            if (cargosWeight <= t.getCapacity()) return true;
//            // clever check!!
//            List<City> route = makeOrderRoute(orderDTO);
//            Map<City, Double> weights = getCitiesWithWeightsOfAddingCargos(orderDTO);
//            double maxweight = 0;
//            double currentweight = 0;
//
//            for (City c : route) {
//                currentweight += weights.get(c);
//                if (currentweight > maxweight) maxweight = currentweight;
//            }
//            if (maxweight > t.getCapacity()) return false;
//        }
//        return true;
//    }


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



    public OrderDTO convertOrderToDTO(Order order){
        if (order == null) return null;
        OrderDTO orderDTO = new OrderDTOImpl();
        orderDTO.setDescription(order.getOrderDescription());
        orderDTO.setDate(order.getOrderDate().toString());
        orderDTO.setAssignedTruck(order.getAssignedTruck().getRegistrationNumber());

        List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
        ArrayList<Cargo> result = new ArrayList<>();
        if (cargos!=null) {

            for (Cargo c : cargos) {
                if (c.getAssignedOrder().getOrderDescription().equals(order.getOrderDescription())) result.add(c);
            }
        }
        int[] cargosId = new int[result.size()];
        for(int i = 0; i < cargosId.length; i++){
            cargosId[i] = result.get(i).getId();
        }
        orderDTO.setCargos(cargosId);
        return orderDTO;
    }

//    @Override
//    public List<City> makeOrderRoute(Order order){
//        if (order == null) return null;
//        OrderDTO orderDTO = convertOrderToDTO(order);
//        return makeOrderRoute(orderDTO);
//    }

    @Override
    public List<City> makeRoute(Order order){
        if (order == null) return null;
        OrderDTO orderDTO = convertOrderToDTO(order);
        return makeRoute(orderDTO);
    }

    @Override
    public List<User> getDriverAssistantsForCurrentOrder(Order currentOrder, User currentDriver) {
        if (currentOrder == null || currentDriver==null) return null;
        Truck t = currentOrder.getAssignedTruck();
        List<User> drivers = userService.getDrivers();
        if (drivers == null) return null;
        List<User> result = new ArrayList<>();
        for(User d: drivers){
            if(d.getDriver().getId()!=currentDriver.getDriver().getId())
                if (d.getDriver().getCurrentTruck().getRegistrationNumber().equals(t.getRegistrationNumber())) result.add(d);
        }
        return result;
    }

    @Override
    public List<Cargo> updateCargosStateInOrder(DriverStateDTO driverStateDTO) {
        if (driverStateDTO == null) return null;
        if (driverStateDTO.getCargoForChangeState() == null) return null;
        if (driverStateDTO.getCargoForChangeState().length == 0) return null;

        if (driverStateDTO.getCargoStateChangeTo() == 0) return null;
        CargoStatus newStatus = CargoStatus.PREPARED;
        if (driverStateDTO.getCargoStateChangeTo() == 1) newStatus = CargoStatus.SHIPPED;
        if(driverStateDTO.getCargoStateChangeTo() == 2) newStatus = CargoStatus.DELIVERED;

        int [] cargosIds = driverStateDTO.getCargoForChangeState();
        int size = cargosIds.length;
        List<Cargo> cargos = new ArrayList<>();
        for(int i = 0; i < size; i++ ){
            Cargo c = cargoDAO.getById(cargosIds[i]);
            if (c!=null){
                c = cargoDAO.update(c.getId(),c.getCargoName(),c.getWeight(),newStatus,c.getAssignedOrder(),c.getLoadPoint(),c.getUnloadPoint());
                cargos.add(c);
            }
        }
        return cargos;
    }


    private List<Cargo> findCargosToUnload(List<Cargo> cargos, City city){

        if (cargos == null || city == null) return null;
        List<Cargo> result = new ArrayList<>();
        for(Cargo c: cargos){
            if (c.getUnloadPoint().getCity().getCityName().equals(city.getCityName())) result.add(c);
        }
        return result;
    }

    @Override
    public List<City> makeRoute(OrderDTO orderDTO){
        if (orderDTO == null) return null;
        if (orderDTO.getCargos() == null) return null;
        if (orderDTO.getCargos().length == 0) return null;
        int[] cargoIDs = orderDTO.getCargos();
        int size = cargoIDs.length;
        List<Cargo> cargos = new ArrayList<>();
        for(int i = 0; i < size; i++){
            Cargo c = cargoDAO.getById(cargoIDs[i]);
            if (c!=null){
                cargos.add(c);
            }
        }
        Set<City> cities = new HashSet<>();
        for(Cargo c: cargos){
            cities.add(c.getLoadPoint().getCity());
            cities.add(c.getUnloadPoint().getCity());
        }
        List<City> citiesForLoadCargos = new ArrayList<>();
        citiesForLoadCargos.addAll(cities);
        List<City> citiesForUnloadCargos = new ArrayList<>();
        //citiesForUnloadCargos.addAll(cities);
        for(City c: citiesForLoadCargos){
           citiesForUnloadCargos.add(0, c);
        }
        for(City c: citiesForLoadCargos){
            //find what is unload in this city
            int index = citiesForLoadCargos.indexOf(c);
            List<Cargo> unloadCargos = findCargosToUnload(cargos, c);
            boolean allCargosLoadedBefore = true;
            for(Cargo cargo: unloadCargos){
                boolean cargoLoadedBefore = false;
                for(int i = 0; i < index; i++){
                    if (cargo.getLoadPoint().getCity().getCityName().equals(citiesForLoadCargos.get(i).getCityName())){
                        cargoLoadedBefore = true;
                    }
                }
                if (!cargoLoadedBefore) allCargosLoadedBefore = false;
            }
            if (allCargosLoadedBefore){
                citiesForUnloadCargos.remove(c);
            }
        }
        List<City> result = new ArrayList<>();
        for(City c: citiesForLoadCargos){
            result.add(c);
        }
        for(City c: citiesForUnloadCargos){
            result.add(c);
        }
        return result;
    }

    @Override
    public boolean updateOrderState(Order order, OrderState newState) {
        if (order == null) return false;
        if (newState == null) return false;
        Order updOrder = orderDAO.getById(order.getOrderId());
        if (updOrder == null) return false;
        orderDAO.update(updOrder.getOrderId(),newState,updOrder.getOrderDescription(),updOrder.getOrderDate(),updOrder.getAssignedTruck());
        return true;
    }

//    @Override
//    public List<City> makeOrderRoute(OrderDTO orderDTO){
//        if (orderDTO == null) return null;
//        List<City> citiesList = new ArrayList<>();
//        citiesList.addAll(getCitiesWithWeightsOfAddingCargos(orderDTO).keySet());
//        City startPoint = null;
//        if (orderDTO.getAssignedTruck() != null) {
//            startPoint = truckDAO.getByRegistrationNumber(orderDTO.getAssignedTruck()).getCurrentCity();
//        }
//
//
//        if (startPoint != null) {
//            if (!citiesList.contains(startPoint)) {
//                // chosen truck is in another city
//                // need to add it into list and make new distances matrix
//                citiesList.add(startPoint);
//            }
//        }
//        double[][] distances = new double[citiesList.size()][citiesList.size()];
//        for(int i = 0; i < distances.length; i++){
//            for(int j = 0; j < distances[i].length; j++){
//                if (i == j){
//                    distances[i][j] = 0;
//                }
//                else {
//                    City cityI = citiesList.get(i);
//                    City cityJ = citiesList.get(j);
//                    Route r = routeDAO.getByCities(cityI, cityJ);
//                    if (r == null) return null;
//                    distances[i][j] = r.getDistance();
//                }
//            }
//        }
//        //
//        int currentIndex;
//        if (startPoint!= null) currentIndex = citiesList.indexOf(startPoint);
//        else currentIndex = 0;
//
//        Set<Integer> excludedIndexes = new HashSet<>();
//        excludedIndexes.add(currentIndex);
//        // list of cities in a route
//        List<City> finalRoute = new ArrayList<>();
//        // count cities with smallest distance between them
//
//
//        while(excludedIndexes.size()!=citiesList.size()) {
//            finalRoute.add(citiesList.get(currentIndex));
//            currentIndex = findMinDistanceElementIndex(distances[currentIndex], excludedIndexes);
//            excludedIndexes.add(currentIndex);
//        }
//        finalRoute.add(citiesList.get(currentIndex));
//        return finalRoute;
//    }


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
