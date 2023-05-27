package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;

public interface IPaymentService {

    double getMileagePoint(double totalCost);
    void pay(int customerId, Order order, double mileagePoint);

}
