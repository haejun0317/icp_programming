package com.sds.icagile.cafe.order.mapper;

import com.sds.icagile.cafe.order.dto.OrderItemDTO;
import com.sds.icagile.cafe.order.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemModelMapper {
    public static OrderItem toEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBeverageId(orderItemDTO.getBeverageId());
        orderItem.setCount(orderItemDTO.getCount());
        return orderItem;
    }

    public static List<OrderItem> toEntity(List<OrderItemDTO> orderItemDTOList) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            OrderItem orderItem = OrderItemModelMapper.toEntity(orderItemDTO);
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
