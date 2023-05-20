package com.sds.icagile.cafe.customer;

import com.sds.icagile.cafe.customer.model.Customer;
import com.sds.icagile.cafe.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(int customerId) throws NotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer is not exist"));
    }
}
