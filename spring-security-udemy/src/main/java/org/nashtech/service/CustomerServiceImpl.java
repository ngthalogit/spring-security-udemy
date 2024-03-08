package org.nashtech.service;

import org.nashtech.model.Customer;
import org.nashtech.model.RegisterCustomer;
import org.nashtech.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public int saveCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public static Customer customerWithRegister(RegisterCustomer registerCustomer) {
        Customer customer = new Customer();
        customer.setEmail(registerCustomer.getEmail());
        customer.setPwd((new BCryptPasswordEncoder()).encode(registerCustomer.getPassword()));
        customer.setRole("user");
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public boolean findByEmail(String email) {
        List<Customer> emails = customerRepository.findByEmail(email);
        return CollectionUtils.isEmpty(emails);
    }
}
