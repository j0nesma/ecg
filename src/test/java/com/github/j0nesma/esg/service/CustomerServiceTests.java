package com.github.j0nesma.esg.service;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.j0nesma.esg.model.Customer;
import com.github.j0nesma.esg.repository.CustomerRepository;
import com.github.j0nesma.esg.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private final Customer customer = new Customer("12345", "John Doe", "123 Elm Street", "", "Springfield", "Some County", "USA", "12345");

    @Test
    void testSaveAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);

        customerService.saveAllCustomers(customers);

        verify(customerRepository, times(1)).saveAll(customers);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        Iterable<Customer> allCustomers = customerService.getAllCustomers();
        assertThat(allCustomers.iterator().next()).usingRecursiveComparison().isEqualTo(customer);
    }


    @Test
    void testGetCustomersById() {
        when(customerRepository.findById(eq(customer.getCustomerRef()))).thenReturn(Optional.of(customer));
        Optional<Customer> customerById = customerService.getCustomerById(customer.getCustomerRef());
        assertThat(customerById.get()).usingRecursiveAssertion().isEqualTo(customer);
    }

    @Test
    void testSaveCustomer() {
        customerService.saveCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }
}
