package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.beverage.BeverageRepository;
import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.beverage.model.BeverageSize;
import com.sds.icagile.cafe.customer.CustomerService;
import com.sds.icagile.cafe.exception.BizException;
import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.order.model.OrderItem;
import com.sds.icagile.cafe.order.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final int CUSTOMER_ID = 12345;
    private static final int PAYMENT_CASH = 1;
    private static final int PAYMENT_CARD = 2;
    private static final int PAYMENT_MILEAGE = 3;
    private static final int AMERICANO_ID = 1;
    private static final int NOT_EXIST_BEVERAGE_ID = 2;

    TestableOrderService subject;

    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private MileageApiService mockMileageApiService;
    @Mock
    private CustomerService mockCustomerService;
    @Mock
    private BeverageRepository mockBeverageRepository;
    @Mock
    private OrderItemRepository mockOrderItemRepository;

    @Captor
    ArgumentCaptor<Mileage> mileageArgumentCaptor;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Captor
    private ArgumentCaptor<List<OrderItem>> orderItemsCaptor;


    private boolean isLastDayOfMonthToday = false;

    @BeforeEach
    public void setUp() throws Exception {
        subject = new TestableOrderService(mockOrderRepository, mockMileageApiService, mockCustomerService, mockBeverageRepository, mockOrderItemRepository);
        when(mockBeverageRepository.getOne(AMERICANO_ID)).thenReturn(new Beverage(AMERICANO_ID, "americano", 1000, BeverageSize.SMALL));
    }

    @Test
    public void 주문을하면_OrderItem들의_가격을_합한_TotalCost를_계산한다() {
        //given

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);
        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        assertEquals(result.getTotalCost(), 2000.0);
    }

    @Test
    public void 등록되지않은_음료가_OrderItem에_포함되면_해당금액은_TotalCost에서_제외한다() {
        //given
        when(mockBeverageRepository.getOne(NOT_EXIST_BEVERAGE_ID)).thenReturn(null);

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Map<String, Object> notValidOrderItem = new HashMap<>();
        notValidOrderItem.put("beverageId", NOT_EXIST_BEVERAGE_ID);
        notValidOrderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Arrays.asList(orderItem, notValidOrderItem), PAYMENT_CASH);

        //then
        assertEquals(result.getTotalCost(), 2000.0);
    }
    
    @Test
    public void 매월_마지막날에_주문하면_TotalCost에서_10퍼센트가_할인된다() {
        //given
        isLastDayOfMonthToday = true;

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        assertEquals(result.getTotalCost(), 1800.0);

        isLastDayOfMonthToday = false;
    }

    @Test
    public void 현금으로_결제시_TotalCost의_10퍼센트를_마일리지로_적립한다() {
        //given

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        assertEquals(result.getMileagePoint(),200.0);
    }

    @Test
    public void 카드로_결제시_TotalCost의_5퍼센트를_마일리지로_적립한다() {
        //given

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CARD);

        //then
        assertEquals(result.getMileagePoint(), 100.0);
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_적으면_예외를_발생한다() {
        //given
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(1000);

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        //then
        assertThrows(BizException.class, () -> subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_MILEAGE));
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_크거나같으면_마일리지API를_호출하여_마일리지를_차감한다() {
        //given
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_MILEAGE);

        //then
        verify(mockMileageApiService).minusMileages(eq(CUSTOMER_ID), mileageArgumentCaptor.capture());

        Mileage appliedMileage = mileageArgumentCaptor.getValue();
        assertEquals(appliedMileage.getValue(), 2000.0);
    }

    @Test
    public void 마일리지로_결제하는경우_마일리지API를_호출하여_마일리지를_적립하지_않는다() {
        //given
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_MILEAGE);

        //then
        verify(mockMileageApiService, never()).saveMileages(anyInt(), any());
    }

    @Test
    public void 마일리지로_결제하지않는경우_마일리지API를_호출하여_마일리지를_적립한다() {
        //given

        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", AMERICANO_ID);
        orderItem.put("count", 2);

        Order result = subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), PAYMENT_CASH);

        //then
        verify(mockMileageApiService).saveMileages(eq(CUSTOMER_ID), mileageArgumentCaptor.capture());
        Mileage appliedMileage = mileageArgumentCaptor.getValue();
        assertEquals(appliedMileage.getValue(), 200.0);
    }

    @Test
    public void 신규주문정보가_저장된다() {
        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", 1);
        orderItem.put("count", 2);
        subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), 1);

        //then
        verify(mockOrderRepository, times(1)).save(orderCaptor.capture());
        Order newOrder = orderCaptor.getValue();
        assertEquals(newOrder.getStatus(), OrderStatus.WAITING);
        assertEquals(newOrder.getPayment(), 1);
        assertEquals(newOrder.getTotalCost(), 2000.0);
        assertEquals(newOrder.getMileagePoint(), 200.0);
    }

    @Test
    public void 주문시_음료목록이_저장된다() {
        //when
        Map<String, Object> orderItem = new HashMap<>();
        orderItem.put("beverageId", 1);
        orderItem.put("count", 2);
        subject.create(CUSTOMER_ID, Collections.singletonList(orderItem), 1);

        //then
        verify(mockOrderItemRepository, times(1)).saveAll(orderItemsCaptor.capture());
        List<OrderItem> orderItems = orderItemsCaptor.getValue();
        OrderItem firstBeverage = orderItems.get(0);
        assertEquals(firstBeverage.getCount(), 2);
    }


    class TestableOrderService extends OrderService {

        public TestableOrderService(OrderRepository orderRepository, MileageApiService mileageApiService, CustomerService customerService, BeverageRepository beverageRepository, OrderItemRepository orderItemRepository) {
            super(orderRepository, mileageApiService, customerService, beverageRepository, orderItemRepository);
        }

        @Override
        protected boolean isLastDayOfMonth() {
            return isLastDayOfMonthToday;
        }
    }
}
