package com.sds.icagile.cafe.api.mileage;

public class Mileage {
    private int customerId;
    private int orderId;
    private double value;

    public Mileage(int customerId, int orderId, double value) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.value = value;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getValue() {
        return value;
    }
}
