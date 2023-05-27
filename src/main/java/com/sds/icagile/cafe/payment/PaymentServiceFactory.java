package com.sds.icagile.cafe.payment;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentServiceFactory {
    private final Map<PaymentType, IPaymentService> serviceMap = new HashMap<>();

    public PaymentServiceFactory(List<IPaymentService> listService) {
        if (CollectionUtils.isEmpty(listService)) {
            throw new IllegalArgumentException("parameter error");
        }

        for (IPaymentService service : listService) {
            serviceMap.put(service.getPaymentType(), service);
        }
    }

    public IPaymentService getService(PaymentType type) {
        if (serviceMap.get(type) == null) {
            throw new IllegalArgumentException("payment type is not support");
        }
        return serviceMap.get(type);
    }
}
