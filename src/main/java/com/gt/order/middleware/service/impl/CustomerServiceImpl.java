package com.gt.order.middleware.service.impl;

import java.util.LinkedHashMap;

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

	//Call client getCustomer API to retrieve customer
	public LinkedHashMap<String, String> retrieveUser(String idType, String idNumber) throws JsonMappingException, JsonProcessingException {
		LinkedHashMap<String, String> customer = exCustomerServiceImpl.getCustomer(idType, idNumber);
		return customer;
	}
}
