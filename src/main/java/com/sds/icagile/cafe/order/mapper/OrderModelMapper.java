package com.sds.icagile.cafe.order.mapper;

import com.sds.icagile.cafe.order.dto.OrderDTO;
import com.sds.icagile.cafe.order.model.Order;

public class OrderModelMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        return orderDTO;
    }
}
