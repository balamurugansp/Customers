package com.example.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.entity.Customer;

 
public interface customerRepository extends  JpaRepository<Customer, Long> {
}
