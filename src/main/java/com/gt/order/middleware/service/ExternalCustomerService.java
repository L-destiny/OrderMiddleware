package com.gt.order.middleware.service;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ExternalCustomerService {
	LinkedHashMap<String, String> getCustomer(String idType, String idNumber) throws JsonMappingException, JsonProcessingException;
}
