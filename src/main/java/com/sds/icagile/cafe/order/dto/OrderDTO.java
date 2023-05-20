package com.sds.icagile.cafe.order.dto;

import java.util.List;

public class OrderDTO {
    private int customerId;
    private List<OrderItemDTO> orderItemDTOList;

    public OrderDTO(int customerId, List<OrderItemDTO> orderItemDTOList) {
        this.customerId = customerId;
        this.orderItemDTOList = orderItemDTOList;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }
}
