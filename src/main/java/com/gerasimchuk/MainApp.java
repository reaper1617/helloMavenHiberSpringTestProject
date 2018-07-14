package com.gerasimchuk;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.DriverDTOImpl;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.OrderDTOImpl;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.DriverService;
import com.gerasimchuk.service.DriverServiceImpl;
import com.gerasimchuk.service.OrderService;
import com.gerasimchuk.service.OrderServiceImpl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public class MainApp {


    public static void main(String[] args) {

        // create
//        ManagerDAO managerDAO = ManagerDAOImpl.getManagerDAOInstance();
//        Manager m1 = managerDAO.create(ManagerPosition.JUNIOR);
////        Manager m2 = managerDAO.create(ManagerPosition.MIDDLE);
////        Manager m3 = managerDAO.create(ManagerPosition.EXPERT);
//
//        CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
//        City c1 = cityDAO.create("City1", CityHasAgency.HAS);
//        City c2 = cityDAO.create("City2", CityHasAgency.HAS);
//        City c3 = cityDAO.create("City3", CityHasAgency.HAS);
//
//        TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();
//        Truck t1 = truckDAO.create("rr88888",5,20,TruckState.NOTREADY,c2);
////        Truck t2 = truckDAO.create("num2",6,22,TruckState.READY,c1);
////        Truck t3 = truckDAO.create("num3",3,12,TruckState.READY,c3);
//
//        DriverDAO driverDAO = DriverDAOImpl.getDriverDAOInstance();
//        Driver d1 = driverDAO.createDriver(10,DriverState.RESTING, c1,t1 );
////        Driver d2 = driverDAO.createDriver(4,DriverState.DRIVING, c2,t2 );
////        Driver d3 = driverDAO.createDriver(22,DriverState.RESTING, c3,t3 );
//
//        UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
//        User ud1 = userDAO.createDriver("Max","Max", "Max","Max",d1);
//        User um1 = userDAO.createManager("Max2","Max2", "Max2","Max2",m1);
//
////        int[] cargos = {277, 280};
////        OrderDTO orderDTO = new OrderDTOImpl("Descr", "12.12.2018",null, cargos);
//
//
////        OrderService orderService = new OrderServiceImpl();
////        System.out.println("validate order dto:" + orderService.validateOrderDTO(orderDTO));
//
//
//        // add routes for cities!!!!
//
//        RouteDAO routeDAO = RouteDAOImpl.getRouteDAOInstance();
////        CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
//
////        City c1 = cityDAO.getByName("City1");
////        City c2 = cityDAO.getByName("City2");
////        City c3 = cityDAO.getByName("City3");
//        routeDAO.create(c1,c2, 100);
//        routeDAO.create(c2,c3, 200);
//        routeDAO.create(c1,c3, 120);
//        routeDAO.create(c2,c1, 100);
//        routeDAO.create(c3,c2, 200);
//        routeDAO.create(c3,c1, 120);


//        List<City> route = orderService.makeOrderRoute(orderDTO);
//
//        for(City c: route){
//            System.out.println(c.getCityName());
//        }

//        User ud2 = userDAO.createDriver("User2","MN2", "LN2","pass2",d2);
//        User ud3 =userDAO.createDriver("User3","MN3", "LN3","pass3",d3);
//        User um1 =userDAO.createManager("Manager1", "sdg","dfhg","dafh",m2);

//        CargoDAO cargoDAO = CargoDAOImpl.getCargoDAOInstance();
//
//        OrderDAO orderDAO = OrderDAOImpl.getOrderDAOInstance();
//        Order order1 = orderDAO.create(OrderState.PREPARED,"Order1", new Timestamp(1000), t1);
//        Order order2 = orderDAO.create(OrderState.PREPARED,"Order2", new Timestamp(1022), t2);
//        Order order3 = orderDAO.create(OrderState.PREPARED,"Order3", new Timestamp(1023), t3);
//
//        RoutepointDAO routepointDAO = RoutepointDAOImpl.getRoutepointDAOInstance();
//        RoutePoint routePoint1 = routepointDAO.create(RoutePointType.LOADING,c1);
//        RoutePoint routePoint2 = routepointDAO.create(RoutePointType.UNLOADING,c1);
//
//        RoutePoint routePoint3 = routepointDAO.create(RoutePointType.LOADING,c1);
//        RoutePoint routePoint4 = routepointDAO.create(RoutePointType.UNLOADING,c3);
//
//        RoutePoint routePoint5 = routepointDAO.create(RoutePointType.LOADING,c2);
//        RoutePoint routePoint6 = routepointDAO.create(RoutePointType.UNLOADING,c3);
//
//        Cargo ca1 = cargoDAO.create("cargo1",10,CargoStatus.PREPARED,order1, routePoint1,routePoint2);
//        Cargo ca2= cargoDAO.create("cargo2",13,CargoStatus.PREPARED,order1, routePoint3,routePoint4);
//        Cargo ca3 = cargoDAO.create("cargo3",3,CargoStatus.PREPARED,order1, routePoint5,routePoint6);
//
//        RouteDAO routeDAO = RouteDAOImpl.getRouteDAOInstance();
//
//        Route r1 = routeDAO.create(c1, c2, 24);
//        Route r2 = routeDAO.create(c2, c3, 13);
//        Route r3 = routeDAO.create(c3, c1, 26);

        // update

//        managerDAO.update(m1.getId(),10,ManagerPosition.EXPERT);
//        cityDAO.update(c1.getId(), "updCity1", CityHasAgency.HASNOT);
//        truckDAO.update(t1.getId(),"fff",4,20,TruckState.NOTREADY,c3);
//        driverDAO.update(d1.getId(),4,DriverState.DRIVING, c3, t1);
//        userDAO.updateDriver(ud3.getId(),"updUD","fgh", "sdfh","gh",d2 );
//        City cityOne = new City("testCity1", CityHasAgency.HAS);
//        City cityTwo = new City("testCity2", CityHasAgency.HAS);
//
//        RoutePoint routePointFirst = new RoutePoint(RoutePointType.LOADING, cityOne);
//        RoutePoint routePointSecond = new RoutePoint(RoutePointType.UNLOADING, cityTwo);
//       cargoDAO.update(ca1.getId(),"updCargo",10,CargoStatus.DELIVERED, order1,routePoint3, routePoint5);
//        orderDAO.update(order1.getOrderId(), OrderState.SHIPPED,"updDescr", new Timestamp(3764),t2);
//       routepointDAO.update(routePoint2.getId(),RoutePointType.UNLOADING,c3);
//        routeDAO.update(r1.getId(),c2,c3,34);


        // delete


//        cargoDAO.delete(ca1.getId());
//
//        routeDAO.delete(r3.getId());
//
//        routepointDAO.delete(routePoint3.getId());
//        orderDAO.delete(order1.getOrderId());
//
//        truckDAO.delete(t1.getId());
//        cityDAO.delete(c1.getId());
//        userDAO.delete(ud1.getId());
//        driverDAO.delete(d1.getId());
//
//        managerDAO.delete(m1.getId());


//        Collection<Cargo> cargos = cargoDAO.getAll();
//
//        for(Cargo c: cargos){
//            cargoDAO.delete(c.getId());
//        }
//
//        Collection<Route> routes = routeDAO.getAll();
//
//        for(Route r: routes){
//            routeDAO.delete(r.getId());
//        }
//
//        Collection<RoutePoint> routePoints = routepointDAO.getAll();
//        for(RoutePoint routePoint : routePoints){
//            routepointDAO.delete(routePoint.getId());
//        }
//
//        Collection<Order> orders = orderDAO.getAll();
//        for(Order o : orders){
//            orderDAO.delete(o.getOrderId());
//        }
//        Collection<User> users = userDAO.getAll();
//        for(User u:users){
//            userDAO.delete(u.getId());
//        }
//        Collection<Driver> drivers = driverDAO.getAll();
//        for(Driver d: drivers){
//            driverDAO.delete(d.getId());
//        }
//
//        Collection<Truck> trucks = truckDAO.getAll();
//        for(Truck t: trucks){
//            truckDAO.delete(t.getId());
//        }
//
//        Collection<City> cities = cityDAO.getAll();
//        for(City c: cities){
//            cityDAO.delete(c.getId());
//        }
//
//
//
//
//
//        Collection<Manager> managers = managerDAO.getAll();
//        for(Manager m: managers){
//            managerDAO.delete(m.getId());
//        }



        DriverDTO driverDTO = new DriverDTOImpl("Maxim","Max","Max","Max","10", "Resting","1","2");

        driverDTO.setDriverId("3");
        DriverService driverService = new DriverServiceImpl();
        driverService.changeDriverInDatabase(driverDTO);





    }


}
