package com.gerasimchuk.service;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.dto.UserDTOImpl;
import com.gerasimchuk.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SignInServiceImpl implements SignInService {


    private UserDAO userDAO;


    @Autowired
    public SignInServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO validate(UserDTO u) {
        // TODO: add some logic
        return null;
    }


    @Override
    public User signIn(UserDTO u) {
        Collection<User> users = userDAO.getAll();
        if (users == null) return null;

        for(User user: users){
            if (user.getUserName().equals(u.getUserName())
                    && user.getMiddleName().equals(u.getMiddleName())
                    && user.getLastName().equals(u.getLastName())
                    && user.getPassword().equals(u.getPassword())){
                return user;
            }
        }
        return null;
    }
}
