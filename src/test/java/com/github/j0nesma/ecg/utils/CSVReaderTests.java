package com.github.j0nesma.ecg.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.github.j0nesma.ecg.model.Customer;


@ExtendWith(MockitoExtension.class)
public class CSVReaderTests {

    private final CSVReader csvReader = new CSVReader();
    private final String HEADER = "CustomerRef,CustomerName,AddressLine1,AddressLine2,Town,County,Country,Postcode";
    private final String CUSTOMER1 = "12345,John Doe,123 Elm Street,,Springfield,Some County,UK,CW98GB";

    @Test
    void testReadCSV() throws IOException {
        List<String> lines = Arrays.asList(HEADER, CUSTOMER1);
        List<Customer> result = csvReader.csvToCustomers(String.join("\n",lines));
        assertEquals(1, result.size());
        Customer customer = result.get(0);
        assertEquals("12345", customer.getCustomerRef());
        assertEquals("John Doe", customer.getCustomerName());
        assertEquals("123 Elm Street", customer.getAddressLine1());
        assertEquals("", customer.getAddressLine2());
        assertEquals("Springfield", customer.getTown());
        assertEquals("UK", customer.getCountry());
        assertEquals("Some County", customer.getCounty());
        assertEquals("CW98GB", customer.getPostcode());
    }
    

    @Test
    void testReadCSV_justHeader() throws IOException {
        List<String> lines = Arrays.asList(HEADER);
        List<Customer> result = csvReader.csvToCustomers(String.join("\n",lines));
        assertEquals(0, result.size());
    }

    @Test
    void testReadCSV_nullString() throws IOException {
        List<Customer> result = csvReader.csvToCustomers(null);
        assertEquals(0, result.size());
    }

    @Test
    void testReadCSV_emptyString() throws IOException {
        List<Customer> result = csvReader.csvToCustomers("");
        assertEquals(0, result.size());
    }

    @Test
    void testReadCSV_blankString() throws IOException {
        List<Customer> result = csvReader.csvToCustomers(" ");
        assertEquals(0, result.size());
    }
}
