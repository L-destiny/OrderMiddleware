package com.gt.order.middleware.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ExternalCustomerService {
	List<ResponseEntity<String>> getCustomer(String idType, String idNumber) throws JsonMappingException, JsonProcessingException;
}
