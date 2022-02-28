package com.gt.order.middleware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gt.order.middleware.service.CustomerService;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping("/api/user")
	public ResponseEntity<LinkedHashMap> retrieveUser(@RequestParam String idType, @RequestParam String idNumber) throws JsonMappingException, JsonProcessingException {
		//return customerService.retrieveUser(idType, idNumber);
		return new ResponseEntity<LinkedHashMap>(customerService.retrieveUser(idType, idNumber), HttpStatus.OK);
	}
}
