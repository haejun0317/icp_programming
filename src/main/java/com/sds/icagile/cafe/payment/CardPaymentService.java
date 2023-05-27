package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class CardPaymentService implements IPaymentService {
    private final MileageApiService mileageApiService;

    public CardPaymentService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    @Override
    public double getMileagePoint(double totalCost) {
        return totalCost * 0.05;
    }

    @Override
    public void pay(int customerId, Order order, double mileagePoint) {
        Mileage mileage = new Mileage(customerId, order.getId(), mileagePoint);
        mileageApiService.saveMileages(customerId, mileage);
        payWithCard(order, customerId);
    }

    private void payWithCard(Order order, int customerId) {
    }
}
