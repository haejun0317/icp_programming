package com.sds.icagile.cafe.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        //TODO - implement
    }

    @Test
    public void 초기데이터에_저장된_ID가_100인_ORDER를_조회한다() {
        //TODO - implement
    }

    @Test
    public void SetUp한_데이터가_조회된다() {
        //TODO - implement
    }
}
