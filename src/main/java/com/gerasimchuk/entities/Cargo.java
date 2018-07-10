package com.gerasimchuk.entities;

import com.gerasimchuk.enums.CargoStatus;

import javax.persistence.*;

@Entity(name = "Cargos")
@Table(name = "cargos",schema = "logisticon", catalog = "")
public class Cargo {
    private int id;
    private String cargoName;
    private double weight;
    private CargoStatus status;
    private Order assignedOrder;
    private RoutePoint loadPoint;
    private RoutePoint unloadPoint;

    public Cargo() {
    }

    public Cargo(String cargoName, double weight, CargoStatus status, RoutePoint loadPoint, RoutePoint unloadPoint) {
        this.cargoName = cargoName;
        this.weight = weight;
        this.status = status;
        this.assignedOrder = null;
        this.loadPoint = loadPoint;
        this.unloadPoint = unloadPoint;
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
    @Column(name = "cargo_name")
    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    @Basic
    @Column(name = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "assigned_order_id")
    public Order getAssignedOrder() {
        return assignedOrder;
    }

    public void setAssignedOrder(Order assignedOrder) {
        this.assignedOrder = assignedOrder;
    }

    @ManyToOne
    @JoinColumn(name = "load_point_id")
    public RoutePoint getLoadPoint() {
        return loadPoint;
    }

    public void setLoadPoint(RoutePoint loadPoint) {
        this.loadPoint = loadPoint;
    }

    @ManyToOne
    @JoinColumn(name = "unload_point_id")
    public RoutePoint getUnloadPoint() {
        return unloadPoint;
    }

    public void setUnloadPoint(RoutePoint unloadPoint) {
        this.unloadPoint = unloadPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cargo cargos = (Cargo) o;

        if (id != cargos.id) return false;
        if (Double.compare(cargos.weight, weight) != 0) return false;
        if (assignedOrder != cargos.assignedOrder) return false;
        if (loadPoint != cargos.loadPoint) return false;
        if (unloadPoint != cargos.unloadPoint) return false;
        if (cargoName != null ? !cargoName.equals(cargos.cargoName) : cargos.cargoName != null) return false;
        if (status != cargos.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (cargoName != null ? cargoName.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + assignedOrder.getOrderId();
        result = 31 * result + loadPoint.getId();
        result = 31 * result + unloadPoint.getId();
        return result;
    }
}
