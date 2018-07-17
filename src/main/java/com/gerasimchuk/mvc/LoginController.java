package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.OrderDAO;
import com.gerasimchuk.dao.OrderDAOImpl;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.SignInService;
import com.gerasimchuk.service.SignInServiceImpl;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private OrderDAO orderDAO = OrderDAOImpl.getOrderDAOInstance();

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(){
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model ui){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {
                List<Order> orders = (ArrayList)orderDAO.getAll();
                ui.addAttribute("ordersList", orders);
                return "/manager/manageraccount";
            }
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User user = LoginStateSaverImpl.getLoggedUser();
                Driver d = user.getDriver();
                DriverController.setParamsForDriverAccountPage(ui,user);
                return "/driver/driveraccount";
            }
        }
        return "/login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserDTOImpl user, BindingResult bindingResult, Model ui){

        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {
                List<Order> orders = (ArrayList)orderDAO.getAll();
                ui.addAttribute("ordersList", orders);
                return "/manager/manageraccount";
            }
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User u = LoginStateSaverImpl.getLoggedUser();
                DriverController.setParamsForDriverAccountPage(ui,u);
            }
        }
        SignInService s = new SignInServiceImpl(UserDAOImpl.getUserDAOInstance());
        User signedUser = s.signIn(user);
        LoginStateSaverImpl.setLoggedUser(signedUser);
        if (signedUser == null) return "/error/errorpage";
        if (signedUser.getRole() == UserRole.DRIVER) {
            User u = LoginStateSaverImpl.getLoggedUser();
            DriverController.setParamsForDriverAccountPage(ui,u);
            return "/driver/driveraccount";
        }
        if (signedUser.getRole() == UserRole.MANAGER){
            List<Order> orders = (ArrayList)orderDAO.getAll();
            ui.addAttribute("ordersList", orders);
            return "/manager/manageraccount";
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
