package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.UsersEntityDAO;
import com.gerasimchuk.dao.UsersEntityDAOImpl;
import com.gerasimchuk.entities.UsersEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {



    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model ui){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        UsersEntityDAO userDAO = new UsersEntityDAOImpl(sessionFactory);
        UsersEntity u2 = userDAO.create("Igor", "Vetkin", "54445");

        UsersEntity user = userDAO.getById(5);




        ui.addAttribute("user1", u2.getUname());
        ui.addAttribute("user2", user.getUname());
        return "/signup/success";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexPost(){

        return "/failed";
    }

}
