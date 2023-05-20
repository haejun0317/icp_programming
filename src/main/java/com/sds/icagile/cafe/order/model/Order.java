package com.sds.icagile.cafe.order.model;

import com.sds.icagile.cafe.customer.model.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"ORDER\"")
public class Order {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private double totalCost;

    @Column
    private double mileagePoint;

    @Column
    private int payment;

    @Column
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    private Customer customer;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        this.orderItems.addAll(orderItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                totalCost == order.totalCost &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalCost, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", status=" + status +
                '}';
    }

    public double getMileagePoint() {
        return mileagePoint;
    }

    public void setMileagePoint(double mileagePoint) {
        this.mileagePoint = mileagePoint;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}