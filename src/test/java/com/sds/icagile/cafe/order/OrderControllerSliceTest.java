package com.sds.icagile.cafe.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
public class OrderControllerSliceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService mockOrderService;

    @Test
    public void OrderId로_GET_API를_요청하면_해당_ORDER를_리턴한다() {
        //TODO - implement

    }

    @Test
    public void 주문정보로_POST_API를_요청하면_해당_주문을_생성한다() {
        //TODO - implement
    }
}
