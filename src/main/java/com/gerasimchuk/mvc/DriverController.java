package com.gerasimchuk.mvc;

import com.gerasimchuk.dto.DriverStateDTOImpl;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.DriverService;
import com.gerasimchuk.service.OrderService;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import com.gerasimchuk.utils.ParamsSetterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class DriverController {


    private OrderService orderService;
    private DriverService driverService;
    private ParamsSetterUtils paramsSetterUtils;

    @Autowired
    public DriverController(OrderService orderService, DriverService driverService, ParamsSetterUtils paramsSetterUtils) {
        this.orderService = orderService;
        this.driverService = driverService;
        this.paramsSetterUtils = paramsSetterUtils;
    }



    @RequestMapping(value = "/driveraccount", method = RequestMethod.GET)
    public String driverAccountGet(Model ui){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User signedUser = LoginStateSaverImpl.getLoggedUser();
                paramsSetterUtils.setParamsForDriverAccountPage(ui,signedUser);
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
            ui.addAttribute("orderStateUpdateSuccess", "Order state updated successfully!");
        }
        return "/driver/driveraccount";
    }
}
