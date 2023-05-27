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
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MileagePaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;

    private MileagePaymentService subject;

    @Mock
    private MileageApiService mockMileageApiService;

    @Captor
    private ArgumentCaptor<Mileage> mileageCaptor;


    @BeforeEach
    public void setUp() {
        subject = new MileagePaymentService(mockMileageApiService);
    }

    @Test
    public void 마일리지로_결제시_TotalCost의_0퍼센트를_마일리지로_적립한다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(2000.0);

        //then
        assertThat(mileagePoint, is(0.0));
    }

    @Test
    public void 마일리지로_결제하는경우_마일리지를_적립하지_않는다() {
        //given
        Order order = Order.builder()
                .totalCost(2000.0)
                .build();
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        subject.pay(CUSTOMER_ID, order, 0.0);

        //then
        verify(mockMileageApiService, never()).saveMileages(anyInt(), any());
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_크거나같으면_마일리지를_차감한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(3000);

        //when
        subject.pay(CUSTOMER_ID, order, 0.0);

        //then
        verify(mockMileageApiService, times(1)).minusMileages(eq(CUSTOMER_ID), mileageCaptor.capture());
        Mileage appliedMileage = mileageCaptor.getValue();
        assertThat(appliedMileage.getValue(), is(2000.0));
    }

    @Test
    public void 마일리지로_결제하는경우_고객의마일리지가_TotalCost보다_적으면_예외를_발생한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        when(mockMileageApiService.getMileages(CUSTOMER_ID)).thenReturn(1000);

        //when
        assertThrows(BizException.class, () -> {
            subject.pay(CUSTOMER_ID, order, 0.0);
        });

        //then
        verify(mockMileageApiService, never()).minusMileages(anyInt(), any());
    }
}