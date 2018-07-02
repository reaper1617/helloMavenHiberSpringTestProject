package com.gerasimchuk.entities;

import com.gerasimchuk.enums.DriverState;

import javax.persistence.*;

@Entity
@Table(name = "driver_status", schema = "logisticon", catalog = "")
public class DriverStatusEntity {
    private int id;
    private DriversEntity assignedDriver;
    private DriverState state;
    private CitiesEntity currentCity;
    private TrucksEntity currentTruck;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToOne(targetEntity = DriversEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_driver_id")
    public DriversEntity getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(DriversEntity assignedDriver) {
        this.assignedDriver = assignedDriver;
    }


    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    public DriverState getState() {
        return state;
    }

    public void setState(DriverState state) {
        this.state = state;
    }

    @OneToOne(targetEntity = CitiesEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "current_city_id")
    public CitiesEntity getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CitiesEntity currentCityId) {
        this.currentCity = currentCityId;
    }

    @OneToOne(targetEntity = TrucksEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "current_truck_id")
    public TrucksEntity getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(TrucksEntity currentTruckId) {
        this.currentTruck = currentTruckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatusEntity that = (DriverStatusEntity) o;

        if (id != that.id) return false;
        if (assignedDriver != that.assignedDriver) return false;
        if (currentCity != that.currentCity) return false;
        if (currentTruck != that.currentTruck) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + assignedDriver.getId();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + currentCity.getId();
        result = 31 * result + currentTruck.getId();
        return result;
    }
}
