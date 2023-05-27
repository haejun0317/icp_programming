package com.sds.icagile.cafe.payment;

import org.springframework.stereotype.Service;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.exception.BizException;
import com.sds.icagile.cafe.order.model.Order;

@Service
public class PaymentService {
    private final MileageApiService mileageApiService;

    public PaymentService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    public double getMileagePoint(int payment, double totalCost) {
        double mileagePoint = 0;
        switch (payment) {
            case 1:
                mileagePoint = totalCost * 0.1;
                break;
            case 2:
                mileagePoint = totalCost * 0.05;
                break;
            case 3:
                break;
        }
        return mileagePoint;
    }

    public void pay(int customerId, int payment, Order order, double mileagePoint) {
        if (payment == 1) {
            Mileage mileage = new Mileage(customerId, order.getId(), mileagePoint);
            mileageApiService.saveMileages(customerId, mileage);
            payWithCash(order, customerId);
        } else if (payment == 2) {
            Mileage mileage = new Mileage(customerId, order.getId(), mileagePoint);
            mileageApiService.saveMileages(customerId, mileage);
            payWithCard(order, customerId);
        } else if (payment == 3) {
            int customerMileage = mileageApiService.getMileages(customerId);
            if (customerMileage >= order.getTotalCost()) {
                Mileage mileage = new Mileage(customerId, order.getId(), order.getTotalCost());
                mileageApiService.minusMileages(customerId, mileage);
            } else {
                throw new BizException("mileage is not enough");
            }
        }
    }

    private void payWithCard(Order order, int customerId) {
    }

    private void payWithCash(Order order, int customerId) {
    }
}
