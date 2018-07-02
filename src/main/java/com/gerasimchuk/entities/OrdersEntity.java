package com.gerasimchuk.entities;

import com.gerasimchuk.enums.OrderState;

import javax.persistence.*;

@Entity
@Table(name = "orders", schema = "logisticon", catalog = "")
public class OrdersEntity {
    private int orderId;
    private OrderState orderState;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (orderId != that.orderId) return false;
        if (orderState != that.orderState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        return result;
    }
}
