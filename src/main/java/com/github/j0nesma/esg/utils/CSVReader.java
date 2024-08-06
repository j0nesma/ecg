package com.github.j0nesma.esg.utils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.github.j0nesma.esg.model.Customer;



@Component
public class CSVReader {
    private final String[] CSV_HEADERS = {"CustomerRef", "CustomerName", "AddressLine1", "AddressLine2", "Town", "County", "Country", "Postcode"};

    public List<Customer> csvToCustomers(String csv) throws IOException  {
        if(isEmpty(csv)) {
            return new ArrayList<>();
        }
        Iterable<CSVRecord> records = getRecords(csv);
        List<Customer> customers = getCustomers(records);
        return customers;
    }

    private List<Customer> getCustomers(Iterable<CSVRecord> records) {
        List<Customer> customers = new ArrayList<>();
        for (CSVRecord record : records) {
            Customer customer;
            customer = new Customer(
                    record.get("CustomerRef"),
                    record.get("CustomerName"),
                    record.get("AddressLine1"),
                    record.get("AddressLine2"),
                    record.get("Town"),
                    record.get("County"),
                    record.get("Country"),
                    record.get("Postcode")
            );
            customers.add(customer);
        }
        return customers;
    }

    private Iterable<CSVRecord> getRecords(String csv) throws IOException {
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
        .setHeader(CSV_HEADERS)
        .setSkipHeaderRecord(true)
        .build()
        .parse(new StringReader(csv));
        return records;
    }

    private boolean isEmpty(String csv) {
        return Objects.isNull(csv) || csv.isEmpty() || csv.isBlank();
    }

    public String getReader(Path path) throws IOException {
        return Files.readString(path);
    }
}
