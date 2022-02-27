package com.gt.order.middleware.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gt.order.middleware.service.CustomerService;
import com.gt.order.middleware.service.external.ExternalCustomerServiceImpl;

@Service
public class CustomerServiceImpl implements CustomerService {
	private ExternalCustomerServiceImpl exCustomerServiceImpl;

	public CustomerServiceImpl(ExternalCustomerServiceImpl exCustomerServiceImpl) {
		super();
		this.exCustomerServiceImpl = exCustomerServiceImpl;
	}

	//Call back end API to retrieve customer
	public List<ResponseEntity<String>> retrieveUser(String idType, String idNumber) throws JsonMappingException, JsonProcessingException {
		List<ResponseEntity<String>> customer = exCustomerServiceImpl.getCustomer(idType, idNumber);
		return customer;
	}
}
