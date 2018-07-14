package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaver;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.gerasimchuk.dao.CityDAOImpl.getCityDAOInstance;

@Controller
public class SignUpController {

    private TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();
    private TruckService truckService = new TruckServiceImpl();


    @RequestMapping(value = "/changedrivers", method = RequestMethod.GET)
    public String changeDriversGet(Model ui){

        // cities list
        List<City> cities =  (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesListChange", cities);
        // trucks list
        List<Truck> trucks = (ArrayList)TruckDAOImpl.getTruckDAOInstance().getAll();
        ui.addAttribute("currentTrucksListChange", trucks);
        //return "/dark-login-form/23-dark-login-form/index";


        return "/manager/changedrivers";
    }

    @RequestMapping(value = "/changedrivers", method = RequestMethod.POST)
    public String changeDriversPost(DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";

        DriverService driverService = new DriverServiceImpl();
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
    public String changeTruckGet(){


        //return "/dark-login-form/23-dark-login-form/index";
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

        //return "/dark-login-form/23-dark-login-form/index";

    }

    @RequestMapping(value = "/manageraccount", method = RequestMethod.GET)
    public String managerAccount(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/manager/manageraccount";
    }

    @RequestMapping(value = "/manageractionsuccess", method = RequestMethod.GET)
    public String managerActionSuccess(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/manager/manageractionsuccess";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) return "/manager/manageraccount";
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) return "/driver/driveraccount";
        }
        //return "/dark-login-form/23-dark-login-form/index";
        return "/login/login";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        if (!LoginStateSaverImpl.getInstance().isLoggedIn()) return "/index";

        LoginStateSaverImpl.setLoggedUser(null);
        //return "/dark-login-form/23-dark-login-form/index";
        return "/logout/logout";
    }



    @RequestMapping(value = "/signupmain", method = RequestMethod.GET)
    public String indexGetSignUp(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/signup/signupmanager";
    }

    @RequestMapping(value = "/addcargoview", method = RequestMethod.GET)
    public String indexGetAddCargoView(){

        return "/cargos/addcargoview";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserDTOImpl user, BindingResult bindingResult, Model ui){

        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) return "/manager/manageraccount";
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) return "/driver/driveraccount";
        }
        SignInService s = new SignInServiceImpl(UserDAOImpl.getUserDAOInstance());
        User signedUser = s.signIn(user);
        LoginStateSaverImpl.setLoggedUser(signedUser);
        if (signedUser == null) return "/error/errorpage";
        if (signedUser.getRole() == UserRole.DRIVER) return "/driver/driveraccount";
        if (signedUser.getRole() == UserRole.MANAGER) return "/manager/manageraccount";
        return "/error/errorpage";
    }


    @RequestMapping(value = "/managetrucks", method = RequestMethod.GET)
    public String manageTrucks(Model ui){

        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";


        List<Truck> trucks = (ArrayList)truckDAO.getAll();

        ui.addAttribute("currentTrucksList", trucks);

        return "/manager/managetrucks";
    }


    @RequestMapping(value = "/managetrucks/{id}", method = RequestMethod.POST)
    public String manageTrucksPOST(@PathVariable("id") int id, TruckDTOImpl truck, BindingResult bindingResult, Model ui ){

        if (id == 0) {
            TruckService truckService = new TruckServiceImpl();
            boolean success = truckService.addTruckToDatabase(truck);

            if (success) {
                ui.addAttribute("addActionSuccess", "Truck successfully added!");
                return "/manager/manageractionsuccess";
            } else return "/error/errorpage";
        }
        if (id == 1){
            ui.addAttribute("changedTruckRegNum", truckDAO.getById(truck.getTruckIdVal()).getRegistrationNumber());

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


        List<User> drivers = new UserServiceImpl().getDrivers();
        ui.addAttribute("currentDriversList", drivers);

        List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesList", cities);

        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksList", trucks);

        return "/manager/managedrivers";
    }

    @RequestMapping(value = "/managedrivers/{id}", method = RequestMethod.POST)
    public String manageDriversPOST(@PathVariable("id") int id, DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui ){

        if (id == 0) {

            DriverService driverService = new DriverServiceImpl();
            boolean success = driverService.addDriverToDatabase(driverDTO);

            if (success) return "/manager/addeddriversuccess";
            else return "/error/errorpage";
        }
        if (id == 1){
            // change driver
            List<City> cities =  (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
            ui.addAttribute("currentCitiesListChange", cities);
            // trucks list
            List<Truck> trucks = (ArrayList)TruckDAOImpl.getTruckDAOInstance().getAll();
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
    public String manageCargos(){
        return "/manager/managecargos";
    }

    @RequestMapping(value = "/managecargos", method = RequestMethod.POST)
    public String manageCargosPOST(CargoDTOImpl cargoDTO, BindingResult bindingResult, Model ui ){


        CargoService cargoService = new CargoServiceImpl();
        boolean success = cargoService.addCargoToDatabase(cargoDTO);


        if (success) return "/manager/addedcargosuccess";
        else return "/error/errorpage";
    }

    @RequestMapping(value = "/manageorders", method = RequestMethod.GET)
    public String manageOrders(){
        return "/manager/manageorders";
    }



    @RequestMapping(value = "/manageorders", method = RequestMethod.POST)
    public String manageOrdersPost(OrderDTOImpl orderDTO, BindingResult bindingResult, Model ui){



        OrderService orderService = new OrderServiceImpl();
        orderService.addOrderToDatabase(orderDTO);
        ui.addAttribute("orderDescr", orderDTO.getDescription());
//        ui.addAttribute("orderDate", orderDTO.getDate());
//        ui.addAttribute("orderCargos", orderDTO.getCargos());


        return "/manager/addtrucktoorder";
    }

    @RequestMapping(value = "/addtrucktoorder", method = RequestMethod.GET )
    public String addTruckToOrder(){




        return "/manager/addtrucktoorder";
    }


    @RequestMapping(value = "/addtrucktoorder",method = RequestMethod.POST)
    public String addTruckToOrderPost(TruckToOrderDTOimpl truckToOrderDTO, BindingResult bindingResult, Model ui){

//        ui.addAttribute("descr", orderDTO.getDescription());
//        ui.addAttribute("date", orderDTO.getDate());
//        ui.addAttribute("truck", orderDTO.getAssignedTruck());
//        ui.addAttribute("cargos", orderDTO.getCargos());
//
//        int [] cargos = orderDTO.getCargos();
//        if (cargos.length == 0) System.out.println("zero");
//        for( int i = 0; i < cargos.length; i++){
//            System.out.println(cargos[i]);
//        }


        OrderService orderService = new OrderServiceImpl();
        boolean success = orderService.addTruckToOrder(truckToOrderDTO);

        if (!success) return "/error/errorpage";
        return "/manager/addedordersuccess";
    }




}
