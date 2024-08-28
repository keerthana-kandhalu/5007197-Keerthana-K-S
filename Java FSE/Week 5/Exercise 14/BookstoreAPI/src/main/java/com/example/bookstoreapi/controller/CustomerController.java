package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    private List<Customer> customers = new ArrayList<>();

    // POST endpoint to create a new customer from JSON
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return customer;
    }

    // POST endpoint to create a new customer from form data
    @PostMapping("/form")
    public Customer createCustomerFromForm(@RequestParam String name,
                                           @RequestParam String email,
                                           @RequestParam String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customers.add(customer);
        return customer;
    }

    // GET endpoint to retrieve all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }
}