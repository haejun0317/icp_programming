package com.sds.icagile.cafe.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceFactoryTest {

    private PaymentServiceFactory subject;

    @Mock
    private CashPaymentService mockCashPaymentService;

    @Mock
    private CardPaymentService mockCardPaymentService;

    @Mock
    private MileagePaymentService mockMileagePaymentService;

    @BeforeEach
    public void setUp() {
        when(mockCashPaymentService.getPaymentType()).thenReturn(PaymentType.CASH);
        when(mockCardPaymentService.getPaymentType()).thenReturn(PaymentType.CARD);
        when(mockMileagePaymentService.getPaymentType()).thenReturn(PaymentType.MILEAGE);

        subject = new PaymentServiceFactory(
                Arrays.asList(mockCashPaymentService,
                        mockCardPaymentService,
                        mockMileagePaymentService));
    }

    @Test
    public void 현금_결제시_CashPaymentService를_사용한다() {
        //given

        //when
        IPaymentService service = subject.getService(PaymentType.CASH);

        //then
        assertThat(service, is(mockCashPaymentService));
    }
}