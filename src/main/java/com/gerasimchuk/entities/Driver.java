package com.gerasimchuk.entities;

import com.gerasimchuk.enums.DriverState;

import javax.persistence.*;

@Entity(name = "Drivers")
@Table(name = "drivers",schema = "logisticon", catalog = "")
public class Driver {
    private int id;
    private double hoursWorked;
    private DriverState state;
    private City currentCity;
    private Truck currentTruck;

    public Driver() {
    }

    public Driver(double hoursWorked, DriverState state, City currentCity, Truck currentTruck) {
        this.hoursWorked = hoursWorked;
        this.state = state;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hours_worked")
    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }


    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    public DriverState getState() {
        return state;
    }

    public void setState(DriverState state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "current_city_id")
    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCityId) {
        this.currentCity = currentCityId;
    }

    @ManyToOne
    @JoinColumn(name = "current_truck_id")
    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruckId) {
        this.currentTruck = currentTruckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver drivers = (Driver) o;

        if (id != drivers.id) return false;
        if (Double.compare(drivers.hoursWorked, hoursWorked) != 0) return false;
        if (currentCity != drivers.currentCity) return false;
        if (currentTruck != drivers.currentTruck) return false;
        if (state != drivers.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(hoursWorked);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + currentCity.getId();
        result = 31 * result + currentTruck.getId();
        return result;
    }
}
