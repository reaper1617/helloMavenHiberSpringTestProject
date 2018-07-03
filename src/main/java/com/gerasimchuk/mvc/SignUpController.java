package com.gerasimchuk.mvc;


import com.gerasimchuk.entities.User;
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

//        if (user!=null) {
//            UsersEntityDAO usersDAO = UsersEntityDAOImpl.getUsersEntityDAOInstance();



     //       System.out.println("UserDAO not null:" + usersDAO != null);

//
//            UsersEntity createdUser = usersDAO.create(user.getUserName(),
//                    user.getMiddleName(),
//                    user.getLastname(),
//                    user.getPassword(),
//                    UserRole.DRIVER,
//                   4, // user.getUserManager(),
//                    5); //user.getUserDriver());

        //    System.out.println("CreatedUser not null:" + createdUser!=null);
//
//            ui.addAttribute("createdUserName", createdUser.getUserName());
//            ui.addAttribute("createdUserMiddleName", createdUser.getMiddleName());
//            ui.addAttribute("createdUserLastname", createdUser.getLastname());
//        }
//        else {

            ui.addAttribute("createdUserName", "You're fucked up");
            ui.addAttribute("createdUserMiddleName", "You're fucked up");
            ui.addAttribute("createdUserLastname", "You're fucked up");
       // }
        return "/signup/success";
    }

}
