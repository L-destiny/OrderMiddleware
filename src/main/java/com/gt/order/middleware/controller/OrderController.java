package com.gt.order.middleware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gt.order.middleware.jto.OrderRequest;
import com.gt.order.middleware.model.Customer;
import com.gt.order.middleware.model.Transaction;
import com.gt.order.middleware.service.OrderService;

@RestController
public class OrderController {
	
	private OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@PostMapping("/api/order")
	public ResponseEntity<Transaction> createOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
		return new ResponseEntity<Transaction>(orderService.saveOrder(orderRequest), HttpStatus.OK);
	}
	
	@GetMapping("/api/order/search")
	public ResponseEntity<Customer> searchOrder(@RequestBody Customer customer) {
		//public ResponseEntity<Customer> searchOrder(@RequestBody HashMap<String, Object> requestMap) {
		//ObjectMapper objectMapper = new ObjectMapper();
		//Customer cust = objectMapper.convertValue(requestMap.get("customer"), Customer.class);
		
		
		//TODO: search with join query and pagination
		return new ResponseEntity<Customer>(orderService.searchOrder(customer), HttpStatus.OK);
	}
	
	@PutMapping("/api/order/{trxRefId}/status")
	public ResponseEntity<Transaction> updateOrderStatus(@PathVariable int trxRefId, @RequestBody Transaction trx) throws JsonProcessingException {
		trx.setTrxRefId(trxRefId);
		return new ResponseEntity<Transaction>(orderService.updateOrderStatus(trx), HttpStatus.OK);
	}
}
