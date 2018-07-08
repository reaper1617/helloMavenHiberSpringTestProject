package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.SignInService;
import com.gerasimchuk.service.SignInServiceImpl;
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

}
