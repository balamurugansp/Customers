package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerException;
import com.example.customer.service.customerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class customerController {

	@Autowired
	private customerService customerService;

	@PostMapping("/api/customer/addcustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws CustomerException {
		Customer savedcustomer = customerService.addCustomer(customer);
		return new ResponseEntity<>(savedcustomer, HttpStatus.CREATED);
	}

	@GetMapping("/api/customer/getCustomerbyid/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long customerId) throws Exception {
		Customer customer = customerService.getCustomerById(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping("/api/customer/getCustomers")
	public ResponseEntity<List<Customer>> getAllUsers() {
		List<Customer> users = customerService.getAllCustomers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/api/customer/updateCustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long customerId, @RequestBody Customer customer) {
		customer.setId(customerId);
		Customer updatedCustomer = customerService.updateCustomer(customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}

	@DeleteMapping("/api/customer/deleteCustomer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId) {
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>("Customer successfully deleted!", HttpStatus.OK);
	}
}
