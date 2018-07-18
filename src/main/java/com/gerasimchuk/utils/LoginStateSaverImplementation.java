package com.gerasimchuk.utils;

import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;

import java.util.Collection;
import java.util.LinkedList;

public class LoginStateSaverImplementation implements LoginStateSaver{

    private Collection<User> loggedUsers = new LinkedList<>();
    private static LoginStateSaverImplementation ourInstance = new LoginStateSaverImplementation();
    public static  LoginStateSaverImplementation getInstance() {
        return ourInstance;
    }

    private LoginStateSaverImplementation() {
    }


    public boolean isLoggedIn(User user) {
        if (user == null) return false;
        if (loggedUsers.contains(user)) return true;
        return false;
    }


    public Collection<User> getLoggedUsers() {
        return loggedUsers;
    }


    public boolean addToLoggedUsers(User user) {
        if (user == null) return false;
        loggedUsers.add(user);
        return true;
    }


    public boolean removeFromLoggedUsers(User user) {
        if (user == null) return false;
        if (loggedUsers.contains(user)) loggedUsers.remove(user);
        return true;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
