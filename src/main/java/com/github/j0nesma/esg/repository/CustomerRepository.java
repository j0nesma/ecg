package com.github.j0nesma.esg.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.j0nesma.esg.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{
}
