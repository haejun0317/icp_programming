package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CashPaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;

    private CashPaymentService subject;

    @Mock
    private MileageApiService mockMileageApiService;

    @Captor
    private ArgumentCaptor<Mileage> mileageCaptor;


    @BeforeEach
    public void setUp() {
        subject = new CashPaymentService(mockMileageApiService);
    }

    @Test
    public void 현금으로_결제시_TotalCost의_10퍼센트를_마일리지로_적립한다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(2000.0);

        //then
        assertEquals(200.0, mileagePoint);
    }

    @Test
    public void 현금으로_결제하는경우_마일리지를_적립한다() {
        //given
        Order order = Order.builder()
                .totalCost(2000.0)
                .build();

        //when
        subject.pay(CUSTOMER_ID, order, 200.0);

        //then
        verify(mockMileageApiService, times(1)).saveMileages(eq(CUSTOMER_ID), mileageCaptor.capture());

        Mileage appliedMileage = mileageCaptor.getValue();
        assertEquals(200.0, appliedMileage.getValue());
    }
}