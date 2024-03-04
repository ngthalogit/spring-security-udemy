package org.nashtech.service;

import org.nashtech.model.Customer;
import org.nashtech.model.RegisterCustomer;
import org.nashtech.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int saveCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    @Override
    public Customer customerWithRegister(RegisterCustomer registerCustomer) {
        Customer customer = new Customer();
        customer.setEmail(registerCustomer.getEmail());
        customer.setPwd(passwordEncoder.encode(registerCustomer.getPassword()));
        customer.setRole("user");
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
