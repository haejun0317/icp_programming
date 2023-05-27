package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;

    private PaymentService subject;

    @Mock
    private CashPaymentService mockCashPaymentService;

    @Mock
    private CardPaymentService mockCardPaymentService;

    @Mock
    private MileagePaymentService mockMileagePaymentService;

    @BeforeEach
    public void setUp() {
        subject = new PaymentService(
                mockCashPaymentService,
                mockCardPaymentService,
                mockMileagePaymentService);
    }

    @Test
    public void 현금_결제로_마일리지계산시_CashPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PaymentType.CASH, 200.0);

        //then
        verify(mockCashPaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 카드_결제로_마일리지계산시_CardPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PaymentType.CARD, 200.0);

        //then
        verify(mockCardPaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 마일리지_결제로_마일리지계산시_CardPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PaymentType.MILEAGE, 200.0);

        //then
        verify(mockMileagePaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 현금_결제시_CashPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PaymentType.CASH, order, 200.0);

        //then
        verify(mockCashPaymentService, times(1)).pay(CUSTOMER_ID, order, 200.0);
    }

    @Test
    public void 카드_결제시_CardPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PaymentType.CARD, order, 100.0);

        //then
        verify(mockCardPaymentService, times(1)).pay(CUSTOMER_ID, order, 100.0);
    }

    @Test
    public void 마일리지_결제시_CardPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PaymentType.MILEAGE, order, 0.0);

        //then
        verify(mockMileagePaymentService, times(1)).pay(CUSTOMER_ID, order, 0.0);
    }
}