package com.demo.app.dal;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    List<Customer> findCustomerByName(String name);

    List<Customer> findCustomerByPhoneNumber(String phoneNumber);
}
