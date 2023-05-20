package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.order.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerSliceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService mockOrderService;

    @Test
    public void OrderId로_GET_API를_요청하면_해당_ORDER를_리턴한다() throws Exception {
        Order order = new Order();
        order.setTotalCost(10000);
        order.setStatus(OrderStatus.WAITING);
        when(mockOrderService.getOrder(100)).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/100"))
                .andExpect(jsonPath("$.totalCost").value(10000))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    public void 주문정보로_POST_API를_요청하면_해당_주문을_생성한다() throws Exception {
        Order order = new Order();
        order.setTotalCost(10000);
        order.setStatus(OrderStatus.WAITING);
        when(mockOrderService.create(1, Collections.emptyList(), 3)).thenReturn(order);

        String orderParam = "{\"customerId\": 1,\"payment\": 3,\"orderItems\": []}";
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/orders")
                                .content(orderParam)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCost").value(10000))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(status().isOk());
    }
}
