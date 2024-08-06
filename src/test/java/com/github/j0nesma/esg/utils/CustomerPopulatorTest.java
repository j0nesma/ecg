package com.github.j0nesma.esg.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.j0nesma.esg.model.Customer;
import com.github.j0nesma.esg.service.CustomerService;
import com.github.j0nesma.esg.utils.CSVReader;
import com.github.j0nesma.esg.utils.CustomerPopulator;

@ExtendWith(MockitoExtension.class)
public class CustomerPopulatorTest {
    @Mock
    private CSVReader csvReader;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private final CustomerPopulator customerPopulator = new CustomerPopulator();

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("12345", "John Doe", "123 Elm Street", "", "Springfield", "Some County", "USA", "12345");
    }

    @Test
    void testPopulation_1_Customer() throws IOException {
        when(csvReader.getReader(any(Path.class))).thenReturn("csvData");
        when(csvReader.csvToCustomers(anyString())).thenReturn(Arrays.asList(customer));
        when(customerService.saveCustomer(any())).thenReturn(customer);
        customerPopulator.populate(Path.of(""));
        verify(customerService, times(1)).saveCustomer(any());
    }

    @Test
    void testPopulation_many_Customers() throws IOException {
        when(csvReader.getReader(any(Path.class))).thenReturn("csvData");
        when(csvReader.csvToCustomers(anyString())).thenReturn(Arrays.asList(customer, customer, customer));
        when(customerService.saveCustomer(any())).thenReturn(customer);
        customerPopulator.populate(Path.of(""));
        verify(customerService, times(3)).saveCustomer(any());
    }

    @Test
    void testPopulation_0_Customers() throws IOException {
        when(csvReader.getReader(any(Path.class))).thenReturn("csvData");
        when(csvReader.csvToCustomers(anyString())).thenReturn(Arrays.asList());
        customerPopulator.populate(Path.of(""));
        verify(customerService, times(0)).saveCustomer(any());
    }
}
