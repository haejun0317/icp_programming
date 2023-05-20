package com.sds.icagile.cafe.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void OrderId로_GET_API를_요청하면_해당_ORDER를_리턴한다() throws Exception {
        String orderResult = "{\"id\":100,\"totalCost\":3000.0,\"mileagePoint\":100.0,\"payment\":1,\"status\":\"PREPARING\",\"orderItems\":[{\"id\":1,\"count\":3}],\"customer\":{\"id\":1,\"name\":\"customer1\"}}";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/100"))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.status").value("PREPARING"))
                .andExpect(content().string(is(orderResult)))
                .andExpect(status().isOk());
    }
}