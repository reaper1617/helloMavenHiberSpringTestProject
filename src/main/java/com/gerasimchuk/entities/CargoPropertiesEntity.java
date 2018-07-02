package com.gerasimchuk.entities;

import com.gerasimchuk.enums.CargoStatus;

import javax.persistence.*;

@Entity
@Table(name = "cargo_properties", schema = "logisticon", catalog = "")
public class CargoPropertiesEntity {
    private int id;
    private CargosEntity assignedCargo;
    private RoutePointsEntity assignedRoutePoint;
    private double weight;
    private CargoStatus cargoStatus;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(targetEntity = CargosEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_cargo_id")
    public CargosEntity getAssignedCargo() {
        return assignedCargo;
    }

    public void setAssignedCargo(CargosEntity assignedCargo) {
        this.assignedCargo = assignedCargo;
    }

    @ManyToOne(targetEntity = RoutePointsEntity.class, cascade = CascadeType.ALL)
    @JoinColumn( name = "assigned_route_point_id")
    public RoutePointsEntity getAssignedRoutePoint() {
        return assignedRoutePoint;
    }

    public void setAssignedRoutePoint(RoutePointsEntity assignedRoutePointId) {
        this.assignedRoutePoint = assignedRoutePointId;
    }

    @Basic
    @Column(name = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Column(name = "cargo_status")
    @Enumerated(EnumType.STRING)
    public CargoStatus getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(CargoStatus cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoPropertiesEntity that = (CargoPropertiesEntity) o;

        if (id != that.id) return false;
        if (assignedCargo != that.assignedCargo) return false;
        if (assignedRoutePoint != that.assignedRoutePoint) return false;
        if (Double.compare(that.weight, weight) != 0) return false;
        if (cargoStatus != that.cargoStatus) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + assignedCargo.getId();
        result = 31 * result + assignedRoutePoint.getId();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cargoStatus != null ? cargoStatus.hashCode() : 0);
        return result;
    }
}
