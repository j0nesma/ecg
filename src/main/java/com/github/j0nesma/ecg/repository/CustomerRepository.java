package com.github.j0nesma.ecg.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.j0nesma.ecg.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{
}
