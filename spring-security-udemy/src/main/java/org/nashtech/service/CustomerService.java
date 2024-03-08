package org.nashtech.service;

import org.nashtech.model.Customer;
import org.nashtech.model.RegisterCustomer;

import java.util.List;

public interface CustomerService {
    int saveCustomer(Customer customer);

    List<Customer> getAll();

    boolean findByEmail(String email);
}
