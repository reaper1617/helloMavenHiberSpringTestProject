package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.OrderDAO;
import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.LoginService;
import com.gerasimchuk.service.SignInService;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import com.gerasimchuk.utils.ParamsSetterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private OrderDAO orderDAO;
    private ParamsSetterUtils paramsSetterUtils;
    private SignInService signInService;
    private LoginService loginService;
    private UserDAO userDAO;

    @Autowired
    public LoginController(OrderDAO orderDAO, ParamsSetterUtils paramsSetterUtils, SignInService signInService, LoginService loginService, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.paramsSetterUtils = paramsSetterUtils;
        this.signInService = signInService;
        this.loginService = loginService;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(){
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model ui){
//        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
//            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {
//                List<Order> orders = (ArrayList)orderDAO.getAll();
//                ui.addAttribute("ordersList", orders);
//                return "/manager/manageraccount";
//            }
//            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
//                User user = LoginStateSaverImpl.getLoggedUser();
//                Driver d = user.getDriver();
//                paramsSetterUtils.setParamsForDriverAccountPage(ui,user);
//                return "/driver/driveraccount";
//            }
//        }
        return "/login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserDTOImpl user, BindingResult bindingResult, Model ui){

//        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
//            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {
//                List<Order> orders = (ArrayList)orderDAO.getAll();
//                ui.addAttribute("ordersList", orders);
//                return "/manager/manageraccount";
//            }
//            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
//                User u = LoginStateSaverImpl.getLoggedUser();
//                paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
//            }
//        }

//        User signedUser = signInService.signIn(user);
//        LoginStateSaverImpl.setLoggedUser(signedUser);
//        if (signedUser == null) return "/error/errorpage";
//        if (signedUser.getRole() == UserRole.DRIVER) {
//            User u = LoginStateSaverImpl.getLoggedUser();
//            paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
//            return "/driver/driveraccount";
//        }
//        if (signedUser.getRole() == UserRole.MANAGER){
//            List<Order> orders = (ArrayList)orderDAO.getAll();
//            ui.addAttribute("ordersList", orders);
//            return "/manager/manageraccount";
//        }
        String fullUserName = user.getUserName()+" "+user.getMiddleName()+" "+user.getLastName();
        boolean success =  loginService.login(fullUserName, user.getPassword());

        if (success){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails){
                String fullName = ((UserDetails) principal).getUsername();
                String[] strings = fullName.split(" ");
                User u = userDAO.getByFullName(strings[0], strings[1], strings[2]);
                if (u.getRole() == UserRole.DRIVER) return "/driver/driveraccount";
                if (u.getRole() == UserRole.MANAGER) return "/manager/manageraccount";
            }
        }
        return "/error/errorpage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        if (!LoginStateSaverImpl.getInstance().isLoggedIn()) return "/index";
        LoginStateSaverImpl.setLoggedUser(null);
        return "/logout/logout";
    }
}
