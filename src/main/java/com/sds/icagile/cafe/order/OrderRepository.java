package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatus(OrderStatus status);
}
