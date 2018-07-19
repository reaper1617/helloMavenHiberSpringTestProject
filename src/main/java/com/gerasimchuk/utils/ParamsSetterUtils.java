package com.gerasimchuk.utils;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.CargoService;
import com.gerasimchuk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParamsSetterUtils {


    private UserDAO userDAO;
    private OrderService orderService;
    private CargoService cargoService;

    @Autowired
    public ParamsSetterUtils(UserDAO userDAO, OrderService orderService, CargoService cargoService) {
        this.userDAO = userDAO;
        this.orderService = orderService;
        this.cargoService = cargoService;
    }

    public boolean setParamsForDriverAccountPage(Model ui, User user){
        User loggedUser = userDAO.getById(user.getId());
        if (loggedUser == null) return false;
        ui.addAttribute("driverID", loggedUser.getDriver().getId());
        ui.addAttribute("driverName", loggedUser.getUserName());
        ui.addAttribute("middleName", loggedUser.getMiddleName());
        ui.addAttribute("lastName", loggedUser.getLastName());
        ui.addAttribute("driverStatus", loggedUser.getDriver().getState());
        ui.addAttribute("currentCity", loggedUser.getDriver().getCurrentCity());
        if (loggedUser.getDriver().getCurrentTruck() == null) ui.addAttribute("currentTruck", new Truck("No truck",1,1, TruckState.NOTREADY, new City()));
        else ui.addAttribute("currentTruck", loggedUser.getDriver().getCurrentTruck());
        Order order = orderService.findCurrentOrder(loggedUser.getDriver());
        if (order == null) ui.addAttribute("currentOrder", new Order(OrderState.PREPARED,"No order", new Timestamp(0),null));
        else ui.addAttribute("currentOrder", order);
        List<City> cities = orderService.makeRoute(order);
        if (cities == null) cities=new ArrayList<>();
        if (cities.size() == 0) cities.add(new City("No route", CityHasAgency.HASNOT));
        ui.addAttribute("orderRoute", cities);
        List<User> assistants = orderService.getDriverAssistantsForCurrentOrder(order, loggedUser);
        if (assistants == null ) assistants = new ArrayList<>();
        if (assistants.size()==0) assistants.add(new User("No", "assistants", "found","0000", UserRole.DRIVER, new Driver()));
        ui.addAttribute("Assistants", assistants);
        List<Cargo> cargos = (ArrayList)cargoService.getCargosInCityByOrder(loggedUser);
        if (cargos == null) cargos = new ArrayList<>();
        if (cargos.size() == 0) cargos.add(new Cargo("No cargos found",1,CargoStatus.PREPARED, new RoutePoint(),new RoutePoint()));
        ui.addAttribute("cargosInCurrentCity", cargos);
        return true;
    }
}
