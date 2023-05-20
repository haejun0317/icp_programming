package com.sds.icagile.cafe.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sds.icagile.cafe.beverage.model.Beverage;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private int count;

    @JsonIgnore
    @Transient
    private int orderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "beverage_id", referencedColumnName = "id")
    private Beverage beverage;

    public OrderItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Beverage getBeverage() { return beverage; }
    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id &&
                count == orderItem.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
