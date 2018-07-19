package com.gerasimchuk.service;

import com.gerasimchuk.dto.AdminDTO;

public interface AdminService {


    boolean validateUserDataInAdminDTO(AdminDTO adminDTO);
    boolean addUserToDatabase(AdminDTO adminDTO);



    boolean validateTruckDataInAdminDTO(AdminDTO adminDTO);
    boolean deleteTruckFromDatabase(AdminDTO adminDTO);


    boolean deleteUserDriverFromDatabase(AdminDTO adminDTO);



}
