package com.github.j0nesma.ecg.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.j0nesma.ecg.model.Customer;
import com.github.j0nesma.ecg.service.CustomerService;
import com.github.j0nesma.ecg.utils.CustomerPopulator;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    //Needed due to the application context
    @MockBean
    private CustomerPopulator customerPopulator;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Customer customer = Customer.builder().customerName("Ben").build();

     @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        Iterable<Customer> customers = Arrays.asList(
            customer, 
            customer);
        given(customerService.getAllCustomers()).willReturn(customers);

        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
               .andExpect(status().isCreated())
               .andReturn();

        List<Customer> responseCustomers = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Customer>>(){});
        assertEquals(2, responseCustomers.size());
    }


    @Test
    void test_getById_200() throws Exception {
        given(customerService.getCustomerById(eq("REF"))).willReturn(Optional.of(customer));

        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get("/customer/REF"))
               .andExpect(status().isOk())
               .andReturn();

        Customer response = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
        assertEquals("Ben", response.getCustomerName());
    }

    @Test
    void test_getById_404() throws Exception {
        given(customerService.getCustomerById(eq("REF"))).willReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/UNKNOWN"))
               .andExpect(status().isNotFound());
    }

    @Test
    void test_post() throws Exception {
            given(customerService.saveCustomer(customer)).willReturn(customer);
            mockMvc.perform(MockMvcRequestBuilders.post("/customer").content(asJsonString(customer)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andReturn();
    }

    private String asJsonString(Customer customer) {
        try {
            return new ObjectMapper().writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
