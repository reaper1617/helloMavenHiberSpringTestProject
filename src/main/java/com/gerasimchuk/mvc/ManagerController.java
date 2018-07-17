package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ManagerController {

    private  OrderDAO orderDAO;
    private  TruckDAO truckDAO;
    private  CityDAO cityDAO ;
    private  CargoDAO cargoDAO;
    private  OrderService orderService;
    private  CargoService cargoService;
    private  UserService userService;
    private  TruckService truckService;
    private DriverService driverService;

    @Autowired
    public ManagerController(OrderDAO orderDAO, TruckDAO truckDAO, CityDAO cityDAO, CargoDAO cargoDAO, OrderService orderService, CargoService cargoService, UserService userService, TruckService truckService, DriverService driverService) {
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        this.cityDAO = cityDAO;
        this.cargoDAO = cargoDAO;
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.userService = userService;
        this.truckService = truckService;
        this.driverService = driverService;
    }




    @RequestMapping(value = "/manageraccount", method = RequestMethod.GET)
    public String managerAccount(Model ui){
        List<Order> orders = (ArrayList)orderDAO.getAll();
        ui.addAttribute("ordersList", orders);
        return "/manager/manageraccount";
    }


    @RequestMapping(value = "/managetrucks", method = RequestMethod.GET)
    public String manageTrucks(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";
        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksList", trucks);
        List<City> cities = (ArrayList)cityDAO.getAll();
        ui.addAttribute("citiesList", cities);
        return "/manager/managetrucks";
    }

    @RequestMapping(value = "/managetrucks/{id}", method = RequestMethod.POST)
    public String manageTrucksPOST(@PathVariable("id") int id, TruckDTOImpl truck, BindingResult bindingResult, Model ui ){
        if (id == 0) {
            boolean success = truckService.addTruckToDatabase(truck);
            if (success) {
                ui.addAttribute("addActionSuccess", "Truck successfully added!");
                return "/manager/manageractionsuccess";
            } else return "/error/errorpage";
        }
        if (id == 1){
            ui.addAttribute("changedTruckRegNum", truckDAO.getById(truck.getTruckIdVal()).getRegistrationNumber());
            List<City> cities = (ArrayList)cityDAO.getAll();
            ui.addAttribute("citiesForChoose", cities);
            return "/manager/changetrucks";
            // return "/manager/manageractionsuccess";
        }
        if (id == 2){
            ui.addAttribute("addActionSuccess", "Truck deleting!");
            return "/manager/manageractionsuccess";
        }
        return "/error/errorpage";
    }

    @RequestMapping(value = "/managedrivers", method = RequestMethod.GET)
    public String manageDrivers(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";
        List<User> drivers = userService.getDrivers();
        ui.addAttribute("currentDriversList", drivers);
        List<City> cities = (ArrayList)cityDAO.getAll();
        ui.addAttribute("currentCitiesList", cities);
        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksList", trucks);
        return "/manager/managedrivers";
    }

    @RequestMapping(value = "/managedrivers/{id}", method = RequestMethod.POST)
    public String manageDriversPOST(@PathVariable("id") int id, DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui ){
        if (id == 0) {
            boolean success = driverService.addDriverToDatabase(driverDTO);
            if (success) return "/manager/addeddriversuccess";
            else return "/error/errorpage";
        }
        if (id == 1){
            // change driver
            List<City> cities =  (ArrayList)cityDAO.getAll();
            ui.addAttribute("currentCitiesListChange", cities);
            // trucks list
            List<Truck> trucks = (ArrayList)truckDAO.getAll();
            ui.addAttribute("currentTrucksListChange", trucks);
            //return "/dark-login-form/23-dark-login-form/index";
            ui.addAttribute("changedDriverId", driverDTO.getDriverId());
            return "/manager/changedrivers";
        }
        if (id == 2){
            // delete driver
            ui.addAttribute("addActionSuccess", "Not really deleted but success )))");
            return "/manager/manageractionsuccess";
        }
        return "/error/errorpage";
    }

    @RequestMapping(value = "/managecargos", method = RequestMethod.GET)
    public String manageCargos(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";
        List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
        ui.addAttribute("currentCargosList", cargos);
        List<City> cities = (ArrayList)cityDAO.getAll();
        ui.addAttribute("currentCitiesList", cities);
        return "/manager/managecargos";
    }

    // TODO: delete it later
    @RequestMapping(value = "/addcargoview", method = RequestMethod.GET)
    public String indexGetAddCargoView(){
        return "/cargos/addcargoview";
    }

    @RequestMapping(value = "/managecargos/{id}", method = RequestMethod.POST)
    public String manageCargosPOST(@PathVariable("id") int id, CargoDTOImpl cargoDTO, BindingResult bindingResult, Model ui ){
        if (id == 0) {
            boolean success = cargoService.addCargoToDatabase(cargoDTO);
            if (success) return "/manager/addedcargosuccess";
            else {
                ui.addAttribute("errorMessage", "Can't add cargo!");
                return "/error/errorpage";
            }
        }
        if (id == 1){
            ui.addAttribute("changedCargoId", cargoDTO.getCargoId());
            List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
            ui.addAttribute("currentCargosList", cargos);
            List<City> cities = (ArrayList)cityDAO.getAll();
            ui.addAttribute("currentCitiesList", cities);
            return "/manager/changecargos";
        }
        if (id == 2){
            ui.addAttribute("addActionSuccess", "No actual delete but success!");
            return "/manager/manageractionsuccess";
        }
        else return "/error/errorpage";
    }

    @RequestMapping(value = "/changecargos", method = RequestMethod.GET)
    public String changeCargoGet(Model ui){
        List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
        ui.addAttribute("currentCargosList", cargos);
        List<City> cities = (ArrayList)cityDAO.getAll();
        ui.addAttribute("currentCitiesList", cities);
        return "/manager/changecargos";
    }

    @RequestMapping(value = "/changecargos", method = RequestMethod.POST)
    public String changeCargoPost(CargoDTOImpl cargoDTO,BindingResult bindingResult, Model ui){
        boolean success = cargoService.changeCargoInDatabase(cargoDTO);
        if (success){
            ui.addAttribute("addActionSuccess", "Cargo changed successfully!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "Can not change cargo(");
            return "/error/errorpage";
        }
    }

    @RequestMapping(value = "/changedrivers", method = RequestMethod.GET)
    public String changeDriversGet(Model ui){
        List<City> cities =  (ArrayList)cityDAO.getAll();
        ui.addAttribute("currentCitiesListChange", cities);
        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksListChange", trucks);
        return "/manager/changedrivers";
    }

    @RequestMapping(value = "/changedrivers", method = RequestMethod.POST)
    public String changeDriversPost(DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";
        boolean success = driverService.changeDriverInDatabase(driverDTO);
        if (success) {
            ui.addAttribute("addActionSuccess", "Driver updated successfully!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "Cannot update driver!");
            return "/error/errorpage";
        }
    }


    @RequestMapping(value = "/changetrucks", method = RequestMethod.GET)
    public String changeTruckGet(Model ui){
        List<City> cities = (ArrayList)cityDAO.getAll();
        ui.addAttribute("citiesForChoose", cities);
        return "/manager/changetrucks";
    }


    @RequestMapping(value = "/changetrucks", method = RequestMethod.POST)
    public String changeTruckPost(TruckDTOImpl truck, BindingResult bindingResult, Model ui){

        boolean success = truckService.changeTruckInDatabase(truck);
        if (success) {
            ui.addAttribute("addActionSuccess", "Truck attributes successfully changed!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "failed to change truck attributes");
            return "/error/errorpage";
        }

    }

    @RequestMapping(value = "/manageorders", method = RequestMethod.GET)
    public String manageOrders(Model ui){
        List<Cargo> cargos = (ArrayList)cargoService.getCargosWithoutAssignedOrder();
        ui.addAttribute("cargosWithoutAssignedOrders",cargos);
        return "/manager/manageorders";
    }

    @RequestMapping(value = "/manageorders", method = RequestMethod.POST)
    public String manageOrdersPost(OrderDTOImpl orderDTO, BindingResult bindingResult, Model ui){
        orderService.addOrderToDatabase(orderDTO);
        ui.addAttribute("orderDescr", orderDTO.getDescription());
        List<Truck> truckList = (ArrayList)orderService.getTrucksFitsToOrder(orderDTO);
        ui.addAttribute("truckListFitsToOrder", truckList);
        return "/manager/addtrucktoorder";
    }

    @RequestMapping(value = "/addtrucktoorder", method = RequestMethod.GET )
    public String addTruckToOrder(){
        return "/manager/addtrucktoorder";
    }


    @RequestMapping(value = "/addtrucktoorder",method = RequestMethod.POST)
    public String addTruckToOrderPost(TruckToOrderDTOimpl truckToOrderDTO, BindingResult bindingResult, Model ui){
        boolean success = orderService.addTruckToOrder(truckToOrderDTO);
        if (!success) return "/error/errorpage";
        ui.addAttribute("chosenTruck", truckToOrderDTO.getTruckRegNum());
        ui.addAttribute("createdOrder", truckToOrderDTO.getOrderDescription());
        List<User> drivers = (ArrayList)orderService.getDriversFitToTruckAndOrder(truckToOrderDTO);
        ui.addAttribute("driversList",drivers);
        return "/manager/adddriverstoorder";
    }

    @RequestMapping(value = "/adddriverstoorder",method = RequestMethod.GET)
    public String addDriversToOrderGet(Model ui) {
        // TODO: get only drivers fit to truck!
        List<User> drivers = userService.getDrivers();
        ui.addAttribute("driversList",drivers);
        return "/manager/adddriverstoorder";
    }

    @RequestMapping(value = "/adddriverstoorder",method = RequestMethod.POST)
    public String addDriversToOrderPost(DriversToOrderDTOImpl driversToOrderDTO, BindingResult bindingResult, Model ui) {
        // TODO: check how logics works!!!
        boolean success = orderService.addDriversToOrder(driversToOrderDTO);
        if (success) {
            ui.addAttribute("addActionSuccess", "no actual drivers assign ut success");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "Can not assign drivers with order");
            return "/error/errorpage";
        }
    }

    @RequestMapping(value = "/manageractionsuccess", method = RequestMethod.GET)
    public String managerActionSuccess(){
        return "/manager/manageractionsuccess";
    }
}
