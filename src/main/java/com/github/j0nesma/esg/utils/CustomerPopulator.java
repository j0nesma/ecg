package com.github.j0nesma.esg.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.j0nesma.esg.model.Customer;
import com.github.j0nesma.esg.service.CustomerService;



@Component
public class CustomerPopulator {
    
    Logger logger = LoggerFactory.getLogger(CustomerPopulator.class);
    @Autowired
    private CSVReader csvReader;

    @Autowired
    private CustomerService customerService;

    public void populate(Path path) throws IOException {
        List<Customer> customers = csvReader.csvToCustomers(csvReader.getReader(path));
		populateUsingPost(customers);
    }

    private void populateUsingPost(List<Customer> customers) {
        customers.stream().map(customer -> {
			Customer savedCustomer = customerService.saveCustomer(customer);
			logger.info("Saved user with REF: {}", savedCustomer.getCustomerRef());
            return savedCustomer;
		}).collect(Collectors.toList());
    }
}
