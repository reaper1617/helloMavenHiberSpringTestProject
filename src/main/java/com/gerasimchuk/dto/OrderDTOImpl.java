package com.gerasimchuk.dto;

import com.gerasimchuk.service.OrderService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

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
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC+3:00"));

        switch (month){
            case 1: calendar.set(year, Calendar.JANUARY ,day); break;
            case 2: calendar.set(year, Calendar.FEBRUARY ,day); break;
            case 3: calendar.set(year, Calendar.MARCH ,day); break;
            case 4: calendar.set(year, Calendar.APRIL ,day); break;
            case 5: calendar.set(year, Calendar.MAY ,day); break;
            case 6: calendar.set(year, Calendar.JUNE ,day); break;
            case 7: calendar.set(year, Calendar.JULY ,day); break;
            case 8: calendar.set(year, Calendar.AUGUST ,day); break;
            case 9: calendar.set(year, Calendar.SEPTEMBER ,day); break;
            case 10: calendar.set(year, Calendar.OCTOBER ,day); break;
            case 11: calendar.set(year, Calendar.NOVEMBER ,day); break;
            case 12: calendar.set(year, Calendar.DECEMBER ,day); break;
        }

        // TODO: add method to get time zone by city
         Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return timestamp;
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
