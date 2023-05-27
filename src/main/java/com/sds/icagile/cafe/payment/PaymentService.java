package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentServiceFactory PaymentServiceFactory;

    public PaymentService(PaymentServiceFactory PaymentServiceFactory) {
        this.PaymentServiceFactory = PaymentServiceFactory;
    }

    public double getMileagePoint(PaymentType payment, double totalCost) {
        return this.PaymentServiceFactory.getService(payment).getMileagePoint(totalCost);
    }

    public void pay(int customerId, PaymentType payment, Order order, double mileagePoint) {
        this.PaymentServiceFactory.getService(payment).pay(customerId, order, mileagePoint);
    }

}
