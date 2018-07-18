package com.gerasimchuk.utils;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.entities.User;


public class LoginStateSaverImpl implements LoginStateSaver{

    private static UserDAO userDAO = new UserDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private static User loggedUser = null;
    private static LoginStateSaverImpl ourInstance = new LoginStateSaverImpl();

    public static LoginStateSaverImpl getInstance() {
        return ourInstance;
    }

    private LoginStateSaverImpl() {
    }


    public static synchronized User getLoggedUser(){
        if (loggedUser!=null) loggedUser = userDAO.getById(loggedUser.getId());
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
