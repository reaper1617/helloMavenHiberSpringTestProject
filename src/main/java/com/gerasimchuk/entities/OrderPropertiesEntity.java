package com.gerasimchuk.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_properties", schema = "logisticon", catalog = "")
public class OrderPropertiesEntity {
    private int id;
    private OrdersEntity assignedOrder;
    private String orderDate;
    private TrucksEntity assignedTruck;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(targetEntity = OrdersEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_order_id")
    public OrdersEntity getAssignedOrder() {
        return assignedOrder;
    }

    public void setAssignedOrder(OrdersEntity assignedOrderId) {
        this.assignedOrder = assignedOrderId;
    }

    @Basic
    @Column(name = "order_date")
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @OneToOne(targetEntity = TrucksEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_truck_id")
    public TrucksEntity getAssignedTruck() {
        return assignedTruck;
    }

    public void setAssignedTruck(TrucksEntity assignedTruckId) {
        this.assignedTruck = assignedTruckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPropertiesEntity that = (OrderPropertiesEntity) o;

        if (id != that.id) return false;
        if (assignedOrder != that.assignedOrder) return false;
        if (assignedTruck != that.assignedTruck) return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + assignedOrder.getOrderId();
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + assignedTruck.getId();
        return result;
    }
}
