package com.gt.order.middleware.service;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface CustomerService {
	LinkedHashMap<String, String> retrieveUser(String idType, String idNumber) throws JsonMappingException, JsonProcessingException;
}
