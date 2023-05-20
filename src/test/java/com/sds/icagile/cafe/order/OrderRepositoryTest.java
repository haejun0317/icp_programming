package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.order.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        Order waitingOrder = new Order();
        waitingOrder.setStatus(OrderStatus.WAITING);
        Order canceledOrder = new Order();
        waitingOrder.setStatus(OrderStatus.CANCEL);

        orderRepository.saveAll(Arrays.asList(waitingOrder, canceledOrder));
    }

    @Test
    public void 초기데이터에_저장된_ID가_100인_ORDER를_조회한다() {
        Order order = orderRepository.findById(100).orElse(new Order());

        assertThat(order.getId(), is(100));
        assertThat(order.getStatus(), is(OrderStatus.PREPARING));
    }

    @Test
    public void SetUp한_데이터가_조회된다() {
        List<Order> order = orderRepository.findAllByStatus(OrderStatus.CANCEL);

        assertThat(order.size(), is(1));
        assertThat(order.get(0).getStatus(), is(OrderStatus.CANCEL));
    }
}
