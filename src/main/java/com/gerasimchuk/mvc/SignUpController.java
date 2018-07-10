package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaver;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {



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

//        CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
//        City c = cityDAO.create("PTZ", CityHasAgency.HAS);
//
//        TruckDAO truckDAO= TruckDAOImpl.getTruckDAOInstance();
//        Truck t = truckDAO.create("r33rg", 5,10, TruckState.NOTREADY, c);
//
//        DriverDAO driverDAO = DriverDAOImpl.getDriverDAOInstance();
//        Driver d = driverDAO.createDriver(10,DriverState.RESTING,c,t );
////
//        UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
//        User u = userDAO.createDriver(user.getUserName(), user.getMiddleName(), user.getLastName(),user.getPassword(),d );
//
//            ui.addAttribute("createdUserFirstName", u.getUserName() );
//            ui.addAttribute("createdUserMiddleName", u.getMiddleName());
//            ui.addAttribute("createdUserLastname", u.getLastName());

//        LoginStateSaver loginState = LoginStateSaverImpl.getInstance();
//        if (loginState!=null){
//            if (loginState.isLoggedIn()) {
//                if (LoginStateSaverImpl.getLoggedUser() != null){
//                    if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER)return "/driver/driveraccount";
//                    if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER)return "/manager/manageraccount";
//                }
//            }
//        }



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
    public String manageTrucks(){
        return "/manager/managetrucks";
    }


    @RequestMapping(value = "/managetrucks", method = RequestMethod.POST)
    public String manageTrucksPOST(TruckDTOImpl truck, BindingResult bindingResult, Model ui ){

        TruckService truckService = new TruckServiceImpl();
        boolean success = truckService.addTruckToDatabase(truck);

        if (success) return "/manager/addedtrucksuccess";
        else return "/error/errorpage";
    }

    @RequestMapping(value = "/managedrivers", method = RequestMethod.GET)
    public String manageDrivers(){
        return "/manager/managedrivers";
    }

    @RequestMapping(value = "/managedrivers", method = RequestMethod.POST)
    public String manageDriversPOST(DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui ){


        DriverService driverService = new DriverServiceImpl();
        boolean success = driverService.addDriverToDatabase(driverDTO);

        if (success) return "/manager/addeddriversuccess";
        else return "/error/errorpage";
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

}
