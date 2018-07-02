package com.gerasimchuk;

import com.gerasimchuk.dao.*;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.CityHasAgency;
import com.gerasimchuk.enums.ManagerPosition;
import com.gerasimchuk.enums.OrderState;
import com.gerasimchuk.enums.UserRole;

import java.util.Collection;

public class MainApp {


    public static void main(String[] args) {

//       CitiesEntityDAO citiesDAO = CitiesEntityDAOImpl.getCitiesEntityDAOInstance();
//       citiesDAO.create("Moskow",CityHasAgency.HAS);
//        citiesDAO.create("Saint-Petersburg",CityHasAgency.HAS);
//        citiesDAO.create("Petrozavodsk",CityHasAgency.HAS);
//
//        Collection<CitiesEntity> cities = citiesDAO.getAll();
//        for(CitiesEntity c: cities){
//            System.out.println(c.getCityName() + " " + c.getHasAgency());
//        }


        OrdersEntityDAO ordersDAO = OrdersEntityDAOImpl.getOrdersEntityDAOInstance();
        ordersDAO.create(OrderState.PREPARED);
        ordersDAO.create(OrderState.PREPARED);
        ordersDAO.create(OrderState.PREPARED);

        Collection<OrdersEntity> orders = ordersDAO.getAll();
        for(OrdersEntity o: orders){
            System.out.println(o.getOrderState() + " ");
        }

    }


}
