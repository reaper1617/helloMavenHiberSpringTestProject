package com.gerasimchuk.entities;

import com.gerasimchuk.enums.TruckState;

import javax.persistence.*;

@Entity
@Table(name = "trucks", schema = "logisticon", catalog = "")
public class TrucksEntity {
    private int id;
    private String registrationNumber;
    private int shift;
    private double capacity;
    private TruckState state;
    private CitiesEntity currentCity;

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

    @OneToOne(targetEntity = CitiesEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "current_city_id")
    public CitiesEntity getCurrentCityId() {
        return currentCity;
    }

    public void setCurrentCityId(CitiesEntity currentCityId) {
        this.currentCity = currentCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrucksEntity that = (TrucksEntity) o;

        if (id != that.id) return false;
        if (shift != that.shift) return false;
        if (Double.compare(that.capacity, capacity) != 0) return false;
        if (currentCity != that.currentCity) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;
        if (state != that.state) return false;

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
