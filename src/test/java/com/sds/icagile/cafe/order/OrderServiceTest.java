package com.sds.icagile.cafe.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Test
    public void 주문을하면_OrderItem들의_가격을_합한_TotalCost를_계산한다() {
        //TODO - implement
    }

    @Test
    public void 등록되지않은_음료가_OrderItem에_포함되면_해당금액은_TotalCost에서_제외한다() {
        //TODO - implement
    }
    
    @Test
    public void 매월_마지막날에_주문하면_TotalCost에서_10퍼센트가_할인된다() {
        //TODO - implement
    }

    @Test
    public void 현금으로_결제시_TotalCost의_10퍼센트를_마일리지로_적립한다() {
        //TODO - implement
    }

    @Test
    public void 카드로_결제시_TotalCost의_5퍼센트를_마일리지로_적립한다() {
        //TODO - implement
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_적으면_예외를_발생한다() {
        //TODO - implement
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_크거나같으면_마일리지API를_호출하여_마일리지를_차감한다() {
        //TODO - implement
    }

    @Test
    public void 마일리지로_결제하는경우_마일리지API를_호출하여_마일리지를_적립하지_않는다() {
        //TODO - implement
    }

    @Test
    public void 마일리지로_결제하지않는경우_마일리지API를_호출하여_마일리지를_적립한다() {
        //TODO - implement
    }
}
