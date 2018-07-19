package com.gerasimchuk.service;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Logger;

@Service
public class SignInServiceImpl implements SignInService {


    private UserDAO userDAO;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SignInServiceImpl.class);


    @Autowired
    public SignInServiceImpl(UserDAO userDAO) {
        log.info("Initialization of " + SignInServiceImpl.class.getName() + " started.");
        this.userDAO = userDAO;
        log.info("Initialization of " + SignInServiceImpl.class.getName() + " finished: success.");
    }

    @Override
    public UserDTO validate(UserDTO u) {
        // TODO: add some logic
        return null;
    }


    @Override
    public User signIn(UserDTO u) {
        log.info("Trying to sign in user: " + u.getClass().getName() + " " + u.getUserName() + " " + u.getMiddleName() + " " + u.getLastName());
        Collection<User> users = userDAO.getAll();
        if (users == null){
            log.info("Sign in for user" + u.getClass().getName() + " " + u.getUserName() + " " + u.getMiddleName() + " " + u.getLastName() + " failed: there are no users in database.");
            return null;
        }

        for(User user: users){
            if (user.getUserName().equals(u.getUserName())
                    && user.getMiddleName().equals(u.getMiddleName())
                    && user.getLastName().equals(u.getLastName())
                    && user.getPassword().equals(u.getPassword())){
                log.info("Sign in for user" + u.getClass().getName() + " " + u.getUserName() + " " + u.getMiddleName() + " " + u.getLastName() + " finished: success.");
                return user;
            }
        }
        return null;
    }
}
