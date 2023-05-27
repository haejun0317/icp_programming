package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.exception.BizException;
import com.sds.icagile.cafe.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;
    public static final int PAYMENT_CASH = 1;
    public static final int PAYMENT_CARD = 2;
    public static final int PAYMENT_MILEAGE = 3;

    private PaymentService subject;

    @Mock
    private MileageApiService mockMileageApiService;

    @Captor
    private ArgumentCaptor<Mileage> mileageCaptor;


    @BeforeEach
    public void setUp() {
        subject = new PaymentService(mockMileageApiService);
    }

    @Test
    public void 현금으로_결제시_TotalCost의_10퍼센트를_마일리지로_적립한다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(PAYMENT_CASH, 2000.0);

        //then
        assertEquals(200.0, mileagePoint);
    }

    @Test
    public void 카드로_결제시_TotalCost의_5퍼센트를_마일리지로_적립한다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(PAYMENT_CARD, 2000.0);

        //then
        assertEquals(100.0, mileagePoint);
    }

    @Test
    public void 마일리지로_결제시_TotalCost의_0퍼센트를_마일리지로_적립한다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(PAYMENT_MILEAGE, 2000.0);

        //then
        assertEquals(0.0, mileagePoint);
    }

    @Test
    public void 마일리지로_결제하는경우_마일리지를_적립하지_않는다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_MILEAGE, order, 0.0);

        //then
        verify(mockMileageApiService, never()).saveMileages(anyInt(), any());
    }

    @Test
    public void 현금으로_결제하는경우_마일리지를_적립한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_CARD, order, 100.0);

        //then
        verify(mockMileageApiService, times(1)).saveMileages(eq(CUSTOMER_ID), mileageCaptor.capture());

        Mileage appliedMileage = mileageCaptor.getValue();
        assertEquals(100.0, appliedMileage.getValue());
    }

    @Test
    public void 카드로_결제하는경우_마일리지를_적립한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_CASH, order, 200.0);

        //then
        verify(mockMileageApiService, times(1)).saveMileages(eq(CUSTOMER_ID), mileageCaptor.capture());

        Mileage appliedMileage = mileageCaptor.getValue();
        assertEquals(200.0, appliedMileage.getValue());
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_크거나같으면_마일리지를_차감한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_MILEAGE, order, 0.0);

        //then
        verify(mockMileageApiService, times(1)).minusMileages(eq(CUSTOMER_ID), mileageCaptor.capture());
        Mileage appliedMileage = mileageCaptor.getValue();
        assertEquals(2000.0, appliedMileage.getValue());
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_적으면_예외를_발생한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(1000);

        //when


        //then
        assertThrows(BizException.class, () -> {
            subject.pay(CUSTOMER_ID, PAYMENT_MILEAGE, order, 0.0);
        });
        verify(mockMileageApiService, never()).minusMileages(anyInt(), any());
    }
}