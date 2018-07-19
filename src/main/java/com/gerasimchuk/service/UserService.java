package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.User;

import java.util.List;

public interface UserService {


    List<User> getDrivers();

    static boolean validateName(String name){
        if (name == null) return false;
        if (name.length() > Constants.MAX_NAME_LENGTH) return false;
        for(int i = 0; i < name.length(); i++){
            if (Character.isDigit(name.charAt(i))) return false;
        }
        return true;
    }
}
