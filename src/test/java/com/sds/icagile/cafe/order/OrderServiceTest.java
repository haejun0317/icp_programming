package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.beverage.BeverageRepository;
import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import com.sds.icagile.cafe.customer.CustomerService;
import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    public static final int CUSTOMER_ID = 24264;
    public static final int PAYMENT_CASH = 1;

    private TestableOrderService subject;

    @Mock
    private CustomerService mockCustomerService;

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private MileageApiService mockMileageApiService;

    @Mock
    private BeverageRepository mockBeverageRepository;

    @Mock
    private OrderItemRepository mockOrderItemRepository;

    @Mock
    private PaymentService mockPaymentService;

    private boolean isLastDayOfMonth = false;


    @BeforeEach
    public void setUp() {
        subject = new TestableOrderService(
                mockOrderRepository,
                mockMileageApiService,
                mockCustomerService,
                mockBeverageRepository,
                mockOrderItemRepository,
                mockPaymentService);

        when(mockBeverageRepository.getOne(1)).thenReturn(new Beverage(1, "americano", 1000, BeverageSize.SMALL));
    }

    @Test
    public void 주문을하면_OrderItem들의_가격을_합한_TotalCost를_계산한다() {
        //given
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", 1);
        orderItem.put("count", 2);

        //when
        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        assertEquals(2000.0, result.getTotalCost());
    }

    @Test
    public void 등록되지않은_음료가_OrderItem에_포함되면_해당금액은_TotalCost에서_제외한다() {
        //given
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", 1);
        orderItem.put("count", 2);
        Map<String, Object> notValidOrderItem = new HashMap<>();
        notValidOrderItem.put("beverageId", 2);
        notValidOrderItem.put("count", 3);

        when(mockBeverageRepository.getOne(2)).thenReturn(null);

        //when
        Order result = subject.create(CUSTOMER_ID, Arrays.asList(orderItem, notValidOrderItem), PAYMENT_CASH);

        //then
        assertEquals(2000.0, result.getTotalCost());
    }

    @Test
    public void 매월_마지막날에_주문하면_TotalCost에서_10퍼센트가_할인된다() {
        //given
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", 1);
        orderItem.put("count", 2);
        isLastDayOfMonth = true;

        //when
        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        assertEquals(1800.0, result.getTotalCost());
    }

    class TestableOrderService extends OrderService {
        public TestableOrderService(OrderRepository orderRepository, MileageApiService mileageApiService, CustomerService customerService, BeverageRepository beverageRepository, OrderItemRepository orderItemRepository, PaymentService paymentService) {
            super(orderRepository, mileageApiService, customerService, beverageRepository, orderItemRepository, paymentService);
        }

        @Override
        protected boolean isLastDayOfMonth() {
            return isLastDayOfMonth;
        }
    }
}
