package com.demo.app.controller;

import com.demo.app.dal.Customer;
import com.demo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path="/add-customer")
    public @ResponseBody String addCustomer(Customer customer) {

        customerService.addCustomer(customer);
        return "Saved";
    }

    @GetMapping(path="/update-customer")
    public @ResponseBody String updateCustomer(Customer customer) {

        if (customerService.updateCustomer(customer)) {
            return "Updated";
        }
        return "Not Found";
    }

    @GetMapping(path="/delete-customer")
    public @ResponseBody String deleteCustomer(@RequestParam Integer id) {
        if (customerService.deleteCustomer(id)) {
            return "Deleted";
        }
        return "Not Found";
    }

    @GetMapping(path="/find-customer-by-name")
    public @ResponseBody List<Customer> findCustomerByName(@RequestParam String name) {

        return customerService.findCustomerByName(name);
    }

    @GetMapping(path="/find-customer-by-phone")
    public @ResponseBody List<Customer> findCustomerByPhone(@RequestParam(name = "phone") String phone) {

        return customerService.findCustomerByPhone(phone);
    }
}
