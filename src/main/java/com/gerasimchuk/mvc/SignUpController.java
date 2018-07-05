package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.enums.DriverState;
import com.gerasimchuk.enums.TruckState;
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

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexPost(User user, BindingResult bindingResult, Model ui){

        CityDAO cityDAO = CityDAOImpl.getCityDAOInstance();
        City c = cityDAO.create("PTZ", CityHasAgency.HAS);

        TruckDAO truckDAO= TruckDAOImpl.getTruckDAOInstance();
        Truck t = truckDAO.create("r33rg", 5,10, TruckState.NOTREADY, c);

        DriverDAO driverDAO = DriverDAOImpl.getDriverDAOInstance();
        Driver d = driverDAO.createDriver(10,DriverState.RESTING,c,t );
//
        UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
        User u = userDAO.createDriver(user.getUserName(), user.getMiddleName(), user.getLastName(),user.getPassword(),d );

            ui.addAttribute("createdUserFirstName", u.getUserName() );
            ui.addAttribute("createdUserMiddleName", u.getMiddleName());
            ui.addAttribute("createdUserLastname", u.getLastName());

        return "/signup/success";
    }

}
