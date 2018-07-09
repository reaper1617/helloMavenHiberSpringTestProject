package com.gerasimchuk.service;

import com.gerasimchuk.dto.ManagerDTO;


public interface ManagerService {

    boolean validateManagerDTOData(ManagerDTO managerDTO);

    boolean addManagerToDatabase(ManagerDTO managerDTO);

    static boolean validateManagerPosition(ManagerDTO managerDTO){
        String managerPosition = managerDTO.getManagerPosition();
        if (managerPosition.equals("Junior") ||
                managerPosition.equals("Middle") ||
                managerPosition.equals("Expert")) return true;
        return false;
    }

}
