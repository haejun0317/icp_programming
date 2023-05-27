package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class CashPaymentService implements IPaymentService {
    private final MileageApiService mileageApiService;

    public CashPaymentService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    @Override
    public double getMileagePoint(double totalCost) {
        return totalCost * 0.1;
    }

    @Override
    public void pay(int customerId, Order order, double mileagePoint) {
        Mileage mileage = new Mileage(customerId, order.getId(), mileagePoint);
        mileageApiService.saveMileages(customerId, mileage);
        payWithCash(order, customerId);
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CASH;
    }

    private void payWithCash(Order order, int customerId) {
    }
}
