package com.sds.icagile.cafe.order;

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
    public Order create(@RequestBody Map<String, Object> orderMap) throws Exception {

        int customerId = (int) orderMap.get("customerId");
        int payment = (int) orderMap.get("payment");

        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) orderMap.get("orderItems");

        return orderService.create(customerId, orderItems, payment);
    }
}
