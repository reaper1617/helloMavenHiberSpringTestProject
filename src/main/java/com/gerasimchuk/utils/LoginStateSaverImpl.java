package com.gerasimchuk.utils;

import com.gerasimchuk.entities.User;

public class LoginStateSaverImpl implements LoginStateSaver{

    private static User loggedUser;
    private static LoginStateSaverImpl ourInstance = new LoginStateSaverImpl();

    public static LoginStateSaverImpl getInstance() {
        return ourInstance;
    }

    private LoginStateSaverImpl() {
    }


    public static User getLoggedUser(){
        return loggedUser;
    }

    public static void setLoggedUser(User user){
        synchronized (LoginStateSaverImpl.class){
            loggedUser = user;
        }
    }

    @Override
    public boolean isLoggedIn() {
        if (loggedUser!=null) return true;
        return false;
    }
}
