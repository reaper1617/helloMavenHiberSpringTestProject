package com.gerasimchuk.service;


import com.gerasimchuk.dao.DriverDAO;
import com.gerasimchuk.dao.OrderDAO;
import com.gerasimchuk.dao.TruckDAO;
import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.dto.AdminDTO;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdminServiceImpl implements AdminService {



    private UserDAO userDAO;
    private TruckDAO truckDAO;
    private DriverDAO driverDAO;
    private OrderDAO orderDAO;

    @Autowired
    public AdminServiceImpl(UserDAO userDAO, TruckDAO truckDAO, DriverDAO driverDAO, OrderDAO orderDAO) {
        this.userDAO = userDAO;
        this.truckDAO = truckDAO;
        this.driverDAO = driverDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public boolean validateUserDataInAdminDTO(AdminDTO adminDTO) {
        if (adminDTO == null) return false;
        if (adminDTO.getUserId() == 0) return false;
        User user = userDAO.getById(adminDTO.getUserId());
        if (user == null) return false;
        return true;
    }


    @Override
    public boolean addUserToDatabase(AdminDTO adminDTO) {
        return false;
    }

    @Override
    public boolean validateTruckDataInAdminDTO(AdminDTO adminDTO) {
        if (adminDTO == null) return false;
        if (adminDTO.getTruckId() == 0) return false;
        Truck t = truckDAO.getById(adminDTO.getTruckId());
        if (t == null) return false;
        return true;
    }


    private boolean deleteTruckFromDrivers(Truck truck, Collection<User> users){
        if (truck == null) return false;
        if (users==null) return false;
        else{
            for(User u: users){
                if (u.getDriver()!=null){
                    Driver d = u.getDriver();
                    if (d.getCurrentTruck()!=null){
                        Truck t = d.getCurrentTruck();
                        if (t.getRegistrationNumber().equals(truck.getRegistrationNumber())){
                            driverDAO.update(d.getId(),d.getHoursWorked(),d.getState(),d.getCurrentCity(), null);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean deleteTruckFromOrders(Truck truck, Collection<Order> orders){
        if (truck == null) return false;
        if (orders == null) return false;
        for(Order o: orders){
            if (o.getAssignedTruck()!=null){
                Truck t = o.getAssignedTruck();
                if (t.getRegistrationNumber().equals(truck.getRegistrationNumber())){
                    orderDAO.update(o.getOrderId(),o.getOrderState(),o.getOrderDescription(),o.getOrderDate(),null);
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteTruckFromDatabase(AdminDTO adminDTO) {
        if (!validateTruckDataInAdminDTO(adminDTO)) return false;
        Truck deletingTruck = truckDAO.getById(adminDTO.getTruckId());
        Collection<User> users = userDAO.getAll();
        deleteTruckFromDrivers(deletingTruck,users);
        Collection<Order> orders = orderDAO.getAll();
        deleteTruckFromOrders(deletingTruck, orders);
        truckDAO.delete(deletingTruck.getId());
        return true;
    }


    @Override
    public boolean deleteUserDriverFromDatabase(AdminDTO adminDTO) {
        if (!validateUserDataInAdminDTO(adminDTO)) return false;
        User user = userDAO.getById(adminDTO.getUserId());
        if (user == null) return false;
        if (user.getDriver() != null){
            Driver d = user.getDriver();
            driverDAO.delete(d.getId());
        }
        userDAO.delete(user.getId());
        return true;
    }
}
