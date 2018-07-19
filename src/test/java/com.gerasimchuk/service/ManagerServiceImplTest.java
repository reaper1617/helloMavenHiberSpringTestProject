package com.gerasimchuk.service;

import com.gerasimchuk.dao.ManagerDAO;
import com.gerasimchuk.dao.ManagerDAOImpl;
import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.dto.ManagerDTO;
import com.gerasimchuk.dto.ManagerDTOImpl;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.ManagerPosition;
import com.gerasimchuk.utils.SessionFactorySingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ManagerServiceImplTest {

    private ManagerDAO managerDAO = new ManagerDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private UserDAO userDAO = new UserDAOImpl(SessionFactorySingleton.getSessionFactoryInstance());
    private ManagerService managerService = new ManagerServiceImpl(managerDAO,userDAO);


//    @AfterEach
//    void tearDown() {
//
//        Collection<User> users = userDAO.getAll();
//        if (users!=null){
//            for(User u: users){
//                if (u.getManager()!=null) {
//                    if (u.getUserName().equals("Manager") &&
//                            u.getMiddleName().equals("Manager") &&
//                            u.getLastName().equals("Manager") &&
//                            u.getPassword().equals("Manager") &&
//                            u.getManager().getManagerPosition() == ManagerPosition.EXPERT) {
//                        int id = u.getManager().getId();
//                        userDAO.delete(u.getId());
//                        managerDAO.delete(id);
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    void addManagerToDatabase() {
//
//        ManagerDTO managerDTO = new ManagerDTOImpl("Manager","Manager","Manager","Manager","Expert");
//
//        boolean success = managerService.addManagerToDatabase(managerDTO);
//
//        Collection<User> users = userDAO.getAll();
//        boolean managerAdded = false;
//        for(User u: users){
//            if (u.getManager()!=null){
//                if (u.getUserName().equals(managerDTO.getUserName()) &&
//                        u.getMiddleName().equals(managerDTO.getMiddleName()) &&
//                        u.getLastName().equals(managerDTO.getLastName()) &&
//                        u.getPassword().equals(managerDTO.getPassword()) &&
//                        u.getManager().getManagerPosition() == managerDTO.getManagerPositionVal()){
//                    managerAdded = true;
//                    break;
//                }
//            }
//        }
//        assertEquals(true,success);
//        assertEquals(true,managerAdded);
//    }
}