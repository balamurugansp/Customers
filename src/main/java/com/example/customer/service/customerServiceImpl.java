/**
 * 
 */
package com.example.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerException;
import com.example.customer.repository.customerRepository;

import lombok.AllArgsConstructor;

/**
 * 
 */

@Service
@AllArgsConstructor
public class customerServiceImpl implements customerService {

	@Autowired
	private customerRepository customerRepository;

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException   {
		Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
		if(existingCustomer.isPresent()) {
			throw new CustomerException("customer already Exist with given email :" + customer.getEmail());
		}
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerById(long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		return optionalCustomer.get();
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer existingCustomer = customerRepository.findById(customer.getId()).get();
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setPhoneNumber(customer.getPhoneNumber());
		Customer updatedCustomer = customerRepository.save(existingCustomer);
		return updatedCustomer;
	}

	@Override
	public void deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);

	}

}
