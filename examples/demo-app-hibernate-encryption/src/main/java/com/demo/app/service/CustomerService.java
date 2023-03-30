package com.demo.app.service;

import com.demo.app.dal.Customer;
import com.demo.app.dal.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerRepository.findById(id);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean updateCustomer(Customer customer) {
        if (customer.getId() != null && customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    public List<Customer> findCustomerByPhone(String phone) {
        return customerRepository.findCustomerByPhone(phone);
    }
}
