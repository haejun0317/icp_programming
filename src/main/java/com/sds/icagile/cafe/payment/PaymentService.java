package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final CashPaymentService cashPaymentService;
    private final CardPaymentService cardPaymentService;
    private final MileagePaymentService mileagePaymentService;

    public PaymentService(CashPaymentService cashPaymentService,
                          CardPaymentService cardPaymentService,
                          MileagePaymentService mileagePaymentService) {
        this.cashPaymentService = cashPaymentService;
        this.cardPaymentService = cardPaymentService;
        this.mileagePaymentService = mileagePaymentService;
    }

    public double getMileagePoint(PaymentType paymentType, double totalCost) {
        if (paymentType == PaymentType.CASH) {
            return cashPaymentService.getMileagePoint(totalCost);
        } else if (paymentType == PaymentType.CARD) {
            return cardPaymentService.getMileagePoint(totalCost);
        } else if (paymentType == PaymentType.MILEAGE) {
            return mileagePaymentService.getMileagePoint(totalCost);
        }
        return 0.0;
    }

    public void pay(int customerId, PaymentType paymentType, Order order, double mileagePoint) {
        if(paymentType == PaymentType.CASH) {
            cashPaymentService.pay(customerId, order, mileagePoint);
        } else if (paymentType == PaymentType.CARD) {
            cardPaymentService.pay(customerId, order, mileagePoint);
        } else if (paymentType == PaymentType.MILEAGE) {
            mileagePaymentService.pay(customerId, order, mileagePoint);
        }
    }
}
