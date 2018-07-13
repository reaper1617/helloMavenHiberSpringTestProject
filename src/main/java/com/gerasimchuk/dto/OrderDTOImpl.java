package com.gerasimchuk.dto;

import com.gerasimchuk.service.OrderService;

import java.sql.Timestamp;
import java.util.Map;

public class OrderDTOImpl implements OrderDTO {

    private String description;
    private String date;
    private String assignedTruck;
    private int[] cargos;

    public OrderDTOImpl() {
    }

    public OrderDTOImpl(String description, String date, String assignedTruck, int[] cargos) {
        this.description = description;
        this.date = date;
        this.assignedTruck = assignedTruck;
        this.cargos = cargos;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public Timestamp getDateVal() {
        if (!OrderService.validateDate(date)) return null;
        int day = (date.charAt(0) - '0' )*10 + (date.charAt(1) - '0');
        int month = (date.charAt(3)-'0')*10 + (date.charAt(4)-'0');
        int year = (date.charAt(6)-'0')*1000 + (date.charAt(7)-'0')*100 + (date.charAt(8)-'0')*10 + (date.charAt(9)-'0');
        // format: dd.mm.yyyy
        return new Timestamp(year,month,day, 0,0,0,0);
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getAssignedTruck() {
        return assignedTruck;
    }

    @Override
    public void setAssignedTruck(String assignedTruck) {
        this.assignedTruck = assignedTruck;
    }

    @Override
    public int[] getCargos() {
        return cargos;
    }

    @Override
    public void setCargos(int[] cargos) {
        this.cargos = cargos;
    }
}
