package com.github.j0nesma.esg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.j0nesma.esg.model.Customer;
import com.github.j0nesma.esg.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    public void saveAllCustomers(List<Customer> customers) {
        repository.saveAll(customers);
    }

    public Iterable<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return repository.findById(id);
    }

}
