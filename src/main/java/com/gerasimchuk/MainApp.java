package com.gerasimchuk;

import com.gerasimchuk.dao.UsersEntityDAO;
import com.gerasimchuk.dao.UsersEntityDAOImpl;
import com.gerasimchuk.entities.UsersEntity;

import java.util.Collection;

public class MainApp {


    public static void main(String[] args) {

        UsersEntityDAO usersEntityDAO = UsersEntityDAOImpl.getUsersEntityDAOInstance();
        usersEntityDAO.create("sgsg",
                "stgfg",
                "dfhgh",
                "zdghgh",
                3,
                2);
        usersEntityDAO.create("swgegsg",
                "stgfwegg",
                "dfhgwegh",
                "zdwgghgh",
                3,
                2);

        usersEntityDAO.create("rgsgsg",
                "stgergfg",
                "dfhgherg",
                "zdgerghgh",
                3,
                2);


        Collection<UsersEntity> collection = usersEntityDAO.getAll();

        for(UsersEntity u : collection){
            System.out.println(u.getUserName());
        }

    }


}
