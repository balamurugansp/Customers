package com.example.customer.service;

import java.util.List;

import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerException;
public interface customerService {

	Customer addCustomer(Customer customer) throws CustomerException;
	Customer getCustomerById(long customerId);
	List<Customer> getAllCustomers();
	Customer updateCustomer(Customer customer);
	void deleteCustomer(long customerId);
}
