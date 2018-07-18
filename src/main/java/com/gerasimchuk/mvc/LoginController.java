package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.OrderDAO;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.service.SignInService;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import com.gerasimchuk.utils.ParamsSetterUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AdminController adminController;

    @Autowired
    public LoginController(OrderDAO orderDAO, ParamsSetterUtils paramsSetterUtils, SignInService signInService, AdminController adminController) {
        this.orderDAO = orderDAO;
        this.paramsSetterUtils = paramsSetterUtils;
        this.signInService = signInService;
        this.adminController = adminController;
    }



    private String ifLogged(Model ui){
        if (LoginStateSaverImpl.getLoggedUser()!=null){
            UserRole role = LoginStateSaverImpl.getLoggedUser().getRole();
            if (role == UserRole.MANAGER){
                List<Order> orders = (ArrayList)orderDAO.getAll();
                ui.addAttribute("ordersList", orders);
                return "/manager/manageraccount";
            }
            if (role == UserRole.DRIVER){
                User signedUser = LoginStateSaverImpl.getLoggedUser();
                paramsSetterUtils.setParamsForDriverAccountPage(ui,signedUser);
                return "/driver/driveraccount";
            }
            if (role == UserRole.ADMIN){
                adminController.setAdminPageParams(ui);
                return "/admin/adminaccount";
            }
        }
        return null;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model ui){
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model ui){
       String redirect = ifLogged(ui);
       if (redirect!=null) return redirect;
       return "/login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserDTOImpl user, BindingResult bindingResult, Model ui){
        String redirect = ifLogged(ui);
        if (redirect!=null) return redirect;
        User signedUser = signInService.signIn(user);
        LoginStateSaverImpl.setLoggedUser(signedUser);
        if (signedUser == null) return "/error/errorpage";
        if (signedUser.getRole() == UserRole.DRIVER) {
            User u = LoginStateSaverImpl.getLoggedUser();
            paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
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
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/index";
        LoginStateSaverImpl.setLoggedUser(null);
        return "/logout/logout";
    }
}
