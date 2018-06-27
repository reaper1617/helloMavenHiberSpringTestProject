package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.UsersEntityDAO;
import com.gerasimchuk.dao.UsersEntityDAOImpl;
import com.gerasimchuk.entities.UsersEntity;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {



    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(){

        return "/signup/failed";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexPost(UsersEntity user, BindingResult bindingResult, Model ui){
        UsersEntityDAO usersDAO = UsersEntityDAOImpl.getUsersEntityDAOInsance();
        UsersEntity createdUser = usersDAO.create(user.getUname(),user.getLastname(),user.getUnumber());

        ui.addAttribute("createdUserName", user.getUname());
        ui.addAttribute("createdUserLastname", user.getLastname());
        ui.addAttribute("createdUserUnumber", user.getUnumber());

        return "/signup/success";
    }

}
