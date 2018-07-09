package com.gerasimchuk.service;

import com.gerasimchuk.constants.Constants;

public interface UserService {

    static boolean validateName(String name){
        if (name == null) return false;
        if (name.length() > Constants.MAX_NAME_LENGTH) return false;
        for(int i = 0; i < name.length(); i++){
            if (Character.isDigit(name.charAt(i))) return false;
        }
        return true;
    }
}
