package com.gerasimchuk.entities;

import com.gerasimchuk.enums.OrderState;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity(name = "Orders")
@Table(name = "orders",schema = "logisticon", catalog = "")
public class Order {
    private int orderId;
    private OrderState orderState;
    private String orderDescription;
    private Timestamp orderDate;
    private Truck assignedTruck;

    public Order() {
    }

    public Order(OrderState orderState, String orderDescription, Timestamp orderDate, Truck assignedTruck) {
        this.orderState = orderState;
        this.orderDescription = orderDescription;
        this.orderDate = orderDate;
        this.assignedTruck = assignedTruck;
    }

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    @Column(name = "order_state")
    @Enumerated(EnumType.STRING)
    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Basic
    @Column(name = "order_description")
    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    @Basic
    @Column(name = "order_date")
    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @ManyToOne
    @JoinColumn(name = "assigned_truck_id")
    public Truck getAssignedTruck() {
        return assignedTruck;
    }

    public void setAssignedTruck(Truck assignedTruckId) {
        this.assignedTruck = assignedTruckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order orders = (Order) o;

        if (orderId != orders.orderId) return false;
        if (assignedTruck != orders.assignedTruck) return false;
        if (orderState != orders.orderState) return false;
        if (orderDescription != null ? !orderDescription.equals(orders.orderDescription) : orders.orderDescription != null)
            return false;
        if (orderDate != null ? !orderDate.equals(orders.orderDate) : orders.orderDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (orderDescription != null ? orderDescription.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + assignedTruck.getId();
        return result;
    }
}
