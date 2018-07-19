package com.gerasimchuk.entities;

import com.gerasimchuk.enums.TruckState;

import javax.persistence.*;

@Entity(name = "Trucks")
@Table(name = "trucks",schema = "logisticon", catalog = "")
public class Truck {
    private int id;
    private String registrationNumber;
    private int shift;
    private double capacity;
    private TruckState state;
    private City currentCity;

    public Truck(String registrationNumber, int shift, double capacity, TruckState state, City currentCity) {
        this.registrationNumber = registrationNumber;
        this.shift = shift;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
    }

    public Truck() {
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
    @Column(name = "registration_number")
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Basic
    @Column(name = "shift")
    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Basic
    @Column(name = "capacity")
    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    public TruckState getState() {
        return state;
    }

    public void setState(TruckState state) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck trucks = (Truck) o;

        if (id != trucks.id) return false;
        if (shift != trucks.shift) return false;
        if (Double.compare(trucks.capacity, capacity) != 0) return false;
        if (currentCity != trucks.currentCity) return false;
        if (registrationNumber != null ? !registrationNumber.equals(trucks.registrationNumber) : trucks.registrationNumber != null)
            return false;
        if (state != trucks.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + shift;
        temp = Double.doubleToLongBits(capacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + currentCity.getId();
        return result;
    }
}
