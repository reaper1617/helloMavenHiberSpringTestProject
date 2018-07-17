package com.gerasimchuk.service;

import com.gerasimchuk.dto.DriverStateDTO;
import com.gerasimchuk.dto.DriversToOrderDTO;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.TruckToOrderDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.OrderState;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    boolean validateOrderDTO(OrderDTO orderDTO);

    boolean addOrderToDatabase(OrderDTO orderDTO);


    boolean addTruckToOrder(TruckToOrderDTO truckToOrderDTO);



    Collection<Truck> getTrucksFitsToOrder(OrderDTO orderDTO);

    boolean addDriversToOrder(DriversToOrderDTO driversToOrderDTO);

    Order findCurrentOrder(Driver d);

    //List<City> makeOrderRoute(Order order);

    List<User> getDriverAssistantsForCurrentOrder(Order currentOrder, User currentDriver);

    List<Cargo> updateCargosStateInOrder(DriverStateDTO driverStateDTO);
    List<City> makeRoute(Order order);
    List<City> makeRoute(OrderDTO orderDTO);
    Collection<User> getDriversFitToTruckAndOrder(TruckToOrderDTO truckToOrderDTO);
    boolean updateOrderState(Order order, OrderState newState);

    static boolean validateDate(String date){
        // format: dd.mm.yyyy
        if (date == null) return false;
        if (date.length() != 10) return false;
        if (!Character.isDigit(date.charAt(0)) ||
                !Character.isDigit(date.charAt(1)) ||
                !Character.isDigit(date.charAt(3)) ||
                !Character.isDigit(date.charAt(4)) ||
                !Character.isDigit(date.charAt(6)) ||
                !Character.isDigit(date.charAt(7)) ||
                !Character.isDigit(date.charAt(8)) ||
                !Character.isDigit(date.charAt(9))) return false;

        if (date.charAt(2)!='.' || date.charAt(5)!='.') return false;

        int day = (date.charAt(0) - '0' )*10 + (date.charAt(1) - '0');
        int month = (date.charAt(3)-'0')*10 + (date.charAt(4)-'0');
        int year = (date.charAt(6)-'0')*1000 + (date.charAt(7)-'0')*100 + (date.charAt(8)-'0')*10 + (date.charAt(9)-'0');

        if (day <= 0 || day > 31) return false;

        if (month <=0 || month > 12) return false;

        if (year < 2018 || year > 3018) return false;

        boolean leapYear = false;
        if (year%4 != 0 || year%100 == 0 && year%400 != 0) leapYear = false;
        else leapYear = true;

        if (leapYear){
            if (day > 29 && month==2 || day > 31 && month!=2) return false;
        }
        else {
            if (day > 28 && month==2 || day > 31 && month!=2) return false;
        }

        return true;
    }
}
