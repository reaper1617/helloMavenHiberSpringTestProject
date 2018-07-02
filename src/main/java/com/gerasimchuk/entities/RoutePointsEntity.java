package com.gerasimchuk.entities;

import com.gerasimchuk.enums.RoutePointType;

import javax.persistence.*;

@Entity
@Table(name = "route_points", schema = "logisticon", catalog = "")
public class RoutePointsEntity {
    private int id;
    private RoutePointType type;
    private OrdersEntity assignedOrder;
    private CitiesEntity city;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "_type")
    @Enumerated(EnumType.STRING)
    public RoutePointType getType() {
        return type;
    }

    public void setType(RoutePointType type) {
        this.type = type;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_order_id")
    public OrdersEntity getAssignedOrder() {
        return assignedOrder;
    }

    public void setAssignedOrder(OrdersEntity assignedOrderId) {
        this.assignedOrder = assignedOrderId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    public CitiesEntity getCity() {
        return city;
    }

    public void setCity(CitiesEntity city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutePointsEntity that = (RoutePointsEntity) o;

        if (id != that.id) return false;
        if (assignedOrder != that.assignedOrder) return false;
        if (city != that.city) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + assignedOrder.getOrderId();
        result = 31 * result + city.getId();
        return result;
    }
}
