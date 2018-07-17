package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.dto.DriverStateDTOImpl;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverController {


    private static UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
    private static OrderService orderService= new OrderServiceImpl();
    private static CargoService cargoService = new CargoServiceImpl();
    private static DriverService driverService = new DriverServiceImpl();


    public static boolean setParamsForDriverAccountPage(Model ui, User user){
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
        List<Cargo> cargos = (ArrayList)cargoService.getCargosInCity(loggedUser.getDriver().getCurrentCity());
        if (cargos == null) cargos = new ArrayList<>();
        if (cargos.size() == 0) cargos.add(new Cargo("No cargos found",1,CargoStatus.PREPARED, new RoutePoint(),new RoutePoint()));
        ui.addAttribute("cargosInCurrentCity", cargos);
        return true;
    }

    @RequestMapping(value = "/driveraccount", method = RequestMethod.GET)
    public String driverAccountGet(Model ui){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User signedUser = LoginStateSaverImpl.getLoggedUser();
                setParamsForDriverAccountPage(ui,signedUser);
                return "/driver/driveraccount";
            }
        }
        ui.addAttribute("errorMessage", "You must be driver to open this page");
        return "/error/errorpage";
    }


    @RequestMapping(value = "/driveraccount/{id}", method = RequestMethod.POST)
    public String changeDriverStatePost(@PathVariable("id") int id, DriverStateDTOImpl driverStateDTO, BindingResult bindingResult, Model ui){
        if (id == 1){
            User u = LoginStateSaverImpl.getLoggedUser();
            driverService.updateDriverState(driverStateDTO,u.getDriver());
            setParamsForDriverAccountPage(ui,u);
            ui.addAttribute("stateUpdateSuccess", "State update successfully");
        }
        if (id == 2){
            User u = LoginStateSaverImpl.getLoggedUser();
            orderService.updateCargosStateInOrder(driverStateDTO);
            setParamsForDriverAccountPage(ui,u);
            ui.addAttribute("cargosStateUpdateSuccess", "Cargos state updated successfully");
        }
        if (id == 3){
            User u = LoginStateSaverImpl.getLoggedUser();
            Order order = orderService.findCurrentOrder(u.getDriver());
            boolean success = orderService.updateOrderState(order, driverStateDTO.getOrderStateVal());
            if (!success) {
                ui.addAttribute("errorMessage", "Can not update order status!");
                return "/error/errorpage";
            }
            ui.addAttribute("orderStateUpdateSuccess", "Order state updated successfully!");
        }
        return "/driver/driveraccount";
    }
}
