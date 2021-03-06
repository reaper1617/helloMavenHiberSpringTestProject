package com.gerasimchuk.mvc;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.dto.DriverStateDTOImpl;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import com.gerasimchuk.utils.ParamsSetterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class DriverController {


    private  OrderService orderService;
    private  DriverService driverService;
    private ParamsSetterUtils paramsSetterUtils;

    @Autowired
    public DriverController(OrderService orderService, DriverService driverService, ParamsSetterUtils paramsSetterUtils) {
        this.orderService = orderService;
        this.driverService = driverService;
        this.paramsSetterUtils = paramsSetterUtils;
    }

    private boolean isAccessGranted(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null ){
            ui.addAttribute("errorMessage", "You must be authorised user to open this page");
            return false;
        }
        if (LoginStateSaverImpl.getLoggedUser().getRole()!= UserRole.DRIVER &&
                LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.ADMIN){
            ui.addAttribute("errorMessage", "You must be driver or system administrator to open this page");
            return false;
        }
        return true;
    }


    @RequestMapping(value = "/driveraccount", method = RequestMethod.GET)
    public String driverAccountGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        User signedUser = LoginStateSaverImpl.getLoggedUser();
        paramsSetterUtils.setParamsForDriverAccountPage(ui,signedUser);
        return "/driver/driveraccount";
    }


    @RequestMapping(value = "/driveraccount/{id}", method = RequestMethod.POST)
    public String changeDriverStatePost(@PathVariable("id") int id, DriverStateDTOImpl driverStateDTO, BindingResult bindingResult, Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        if (id == 1){
            User u = LoginStateSaverImpl.getLoggedUser();
            driverService.updateDriverState(driverStateDTO,u.getDriver());
            paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
            ui.addAttribute("stateUpdateSuccess", "State update successfully");
        }
        if (id == 2){
            User u = LoginStateSaverImpl.getLoggedUser();
            orderService.updateCargosStateInOrder(driverStateDTO);
            paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
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
            paramsSetterUtils.setParamsForDriverAccountPage(ui,u);
            ui.addAttribute("orderStateUpdateSuccess", "Order state updated successfully!");
        }
        return "/driver/driveraccount";
    }
}
