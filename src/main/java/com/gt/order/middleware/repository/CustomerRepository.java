package com.gt.order.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.order.middleware.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
