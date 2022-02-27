package com.gt.order.middleware.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gt.order.middleware.model.Orders;

public interface ExternalOrderService {
	ResponseEntity<String> submitOrders(int customerId, List<Orders> orders) throws JsonProcessingException;
}
