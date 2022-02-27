package com.gt.order.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.order.middleware.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
	
}
