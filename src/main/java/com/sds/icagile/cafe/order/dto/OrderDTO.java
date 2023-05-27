package com.sds.icagile.cafe.order.dto;

import java.util.List;

public class OrderDTO {
    private int customerId;
    private int payment;
    private List<OrderItemDTO> orderItems;
    private int id;

    public OrderDTO() {
    }

    public OrderDTO(int customerId, int payment, List<OrderItemDTO> orderItems) {
        this.customerId = customerId;
        this.payment = payment;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
