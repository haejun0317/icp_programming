package com.sds.icagile.cafe.order.model;

import com.sds.icagile.cafe.customer.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"ORDER\"")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @Transient
    public int customerId;

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
}