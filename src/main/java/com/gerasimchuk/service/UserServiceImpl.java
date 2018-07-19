package com.gerasimchuk.service;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getDrivers() {
        List<User> users = (ArrayList)userDAO.getAll();
        List<User> drivers = new ArrayList<>();
        for(User u: users){
            if (u.getRole() == UserRole.DRIVER) drivers.add(u);
        }
        return drivers;
    }
}
