package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.order.dto.OrderDTO;
import com.sds.icagile.cafe.order.mapper.OrderItemModelMapper;
import com.sds.icagile.cafe.order.mapper.OrderModelMapper;
import com.sds.icagile.cafe.order.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public Order get(@PathVariable int orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO order) {
        return OrderModelMapper.toDTO(orderService.create(order.getCustomerId(),
                OrderItemModelMapper.toEntity(order.getOrderItems()),
                order.getPayment()));
    }
}
