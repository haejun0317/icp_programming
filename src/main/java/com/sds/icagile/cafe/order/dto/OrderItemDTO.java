package com.sds.icagile.cafe.order.dto;

public class OrderItemDTO {
    private int beverageId;
    private int count;

    public OrderItemDTO(int beverageId, int count) {
        this.beverageId = beverageId;
        this.count = count;
    }

    public int getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(int beverageId) {
        this.beverageId = beverageId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
