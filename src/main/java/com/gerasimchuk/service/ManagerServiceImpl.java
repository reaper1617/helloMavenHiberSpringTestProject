package com.gerasimchuk.service;

import com.gerasimchuk.dao.ManagerDAO;
import com.gerasimchuk.dao.ManagerDAOImpl;
import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dao.UserDAOImpl;
import com.gerasimchuk.dto.ManagerDTO;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.gerasimchuk.service.UserService.validateName;

@Service
public class ManagerServiceImpl implements ManagerService {

    private  ManagerDAO managerDAO;
    private  UserDAO userDAO;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public ManagerServiceImpl(ManagerDAO managerDAO, UserDAO userDAO, BCryptPasswordEncoder encoder) {
        this.managerDAO = managerDAO;
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Override
    public boolean validateManagerDTOData(ManagerDTO managerDTO) {
        if (managerDTO == null) return false;
        // validate name
        if (!validateName(managerDTO.getUserName())) return false;
        if (!validateName(managerDTO.getMiddleName())) return false;
        if (!validateName(managerDTO.getLastName())) return false;
        //validate manager position
        if (!ManagerService.validateManagerPosition(managerDTO)) return false;
        return true;
    }

    @Override
    public boolean addManagerToDatabase(ManagerDTO managerDTO) {
        if (!validateManagerDTOData(managerDTO));

        Collection<User> users = userDAO.getAll();
        if (users != null){

            for(User u: users){

                if (u.getManager() != null){
                    if (u.getUserName().equals(managerDTO.getUserName()) &&
                            u.getMiddleName().equals(managerDTO.getMiddleName()) &&
                            u.getLastName().equals(managerDTO.getLastName())){
                        return false;
                    }
                }
            }
        }

        Manager manager = managerDAO.create(managerDTO.getManagerPositionVal());
        User user = userDAO.createManager(managerDTO.getUserName(),
                    managerDTO.getMiddleName(),
                    managerDTO.getLastName(),
                    encoder.encode(managerDTO.getPassword()),
                    manager);
        return true;
    }


}
