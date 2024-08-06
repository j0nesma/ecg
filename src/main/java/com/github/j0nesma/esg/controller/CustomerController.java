package com.github.j0nesma.esg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.j0nesma.esg.model.Customer;
import com.github.j0nesma.esg.service.CustomerService;

import lombok.AllArgsConstructor;

@RequestMapping("/customer")
@RestController
@AllArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        Iterable<Customer> customer = customerService.getAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomersById(@PathVariable String id) {
        Optional<Customer> optional = customerService.getCustomerById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
   
    }
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
