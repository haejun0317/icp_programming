package com.sds.icagile.cafe.order;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.beverage.BeverageRepository;
import com.sds.icagile.cafe.beverage.model.Beverage;
import com.sds.icagile.cafe.customer.CustomerService;
import com.sds.icagile.cafe.customer.model.Customer;
import com.sds.icagile.cafe.exception.NotFoundException;
import com.sds.icagile.cafe.order.model.Order;
import com.sds.icagile.cafe.order.model.OrderItem;
import com.sds.icagile.cafe.order.model.OrderStatus;
import com.sds.icagile.cafe.payment.IPaymentService;
import com.sds.icagile.cafe.payment.PaymentServiceFactory;
import com.sds.icagile.cafe.payment.PaymentType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 2021.02.20 홍길동
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MileageApiService mileageApiService;
    private final CustomerService customerService;
    private final BeverageRepository beverageRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentServiceFactory paymentServiceFactory;

    public OrderService(OrderRepository orderRepository,
                        MileageApiService mileageApiService,
                        CustomerService customerService,
                        BeverageRepository beverageRepository,
                        OrderItemRepository orderItemRepository,
                        PaymentServiceFactory paymentServiceFactory) {
        this.orderRepository = orderRepository;
        this.mileageApiService = mileageApiService;
        this.customerService = customerService;
        this.beverageRepository = beverageRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentServiceFactory = paymentServiceFactory;
    }

    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found."));
    }

    public List<OrderItem> getOrderItems(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found."));

        return order.getOrderItems();
    }

    public OrderStatus getOrderStatus(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found."));

        return order.getStatus();
    }

    public Customer getCustomer(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found."));

        return order.getCustomer();
    }

    @Transactional
    public Order create(int customerId, List<Map<String, Object>> orderItemList, int payment) {
        Customer customer = customerService.getCustomer(customerId);

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.WAITING)
                .payment(payment)
                .build();

        IPaymentService service = paymentServiceFactory.getService(PaymentType.fromCode(payment));

        List<OrderItem> orderItems = getOrderItems(orderItemList, order);
        order.setTotalCost(this.getDiscountedTotalCost(getTotalCost(orderItems)));
        order.setMileagePoint(service.getMileagePoint(order.getTotalCost()));
        service.pay(customerId, order, order.getMileagePoint());

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return order;
    }

    private double getTotalCost(List<OrderItem> orderItems) {
        double totalCost = 0;
        for (OrderItem orderItem : orderItems) {
            totalCost += orderItem.getCount() * orderItem.getBeverage().getCost();
        }
        return totalCost;
    }

    private List<OrderItem> getOrderItems(List<Map<String, Object>> orderItemList, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(Map<String, Object> orderItemMap: orderItemList) {
            Beverage beverage = beverageRepository.getOne((Integer) orderItemMap.get("beverageId"));
            if(beverage == null) {
                continue;
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setCount((Integer) orderItemMap.get("count"));
            orderItem.setBeverage(beverage);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private double getDiscountedTotalCost(double totalCost) {
        if(isLastDayOfMonth()) {
            totalCost = totalCost * 0.9;
        }
        return totalCost;
    }

    protected boolean isLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DATE);
        int todayDate = cal.get(Calendar.DATE);

        return lastDayOfMonth == todayDate;
    }

    /**
     *
     * @param orderId
     */
    @Transactional
    public void cancel(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order is not found."));

        if (OrderStatus.WAITING.equals(order.getStatus())) {
            order.setStatus(OrderStatus.CANCEL);
            orderRepository.save(order);

            Mileage mileage = new Mileage(order.getCustomer().getId(), order.getId(), order.getMileagePoint());
            mileageApiService.minusMileages(order.getCustomer().getId(), mileage);

            payCancel(order.getPayment());
        }
    }

    private void payCancel(int payment) {
        switch (payment) {
            case 1:
                cancelCash();
            case 2:
                cancelCard();
            case 3:
                cancelMileage();
            default:
                break;
        }
    }

    private void cancelMileage() {
    }

    private void cancelCard() {
    }

    private void cancelCash() {
    }

}
