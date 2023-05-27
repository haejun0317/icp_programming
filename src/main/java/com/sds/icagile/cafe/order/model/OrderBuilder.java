package com.sds.icagile.cafe.order.model;

import com.sds.icagile.cafe.customer.model.Customer;

public class OrderBuilder {
    private int id;
    private double totalCost;
    private double mileagePoint;
    private int payment;
    private OrderStatus status;
    private Customer customer;

    public OrderBuilder() {
    }

    public OrderBuilder id(int id) {
        this.id = id;
        return this;
    }

    public OrderBuilder totalCost(double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderBuilder mileagePoint(double mileagePoint) {
        this.mileagePoint = mileagePoint;
        return this;
    }

    public OrderBuilder payment(int payment) {
        this.payment = payment;
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Order build() {
        return new Order(id, totalCost, mileagePoint, payment, status, customer);
    }
}