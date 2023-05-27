package com.sds.icagile.cafe.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sds.icagile.cafe.beverage.model.Beverage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
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

    @Transient
    private int beverageId;

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
