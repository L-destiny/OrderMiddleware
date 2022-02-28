package com.gt.order.middleware.service.external;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.order.middleware.model.Customer;
import com.gt.order.middleware.service.ExternalCustomerService;

@Service
public class ExternalCustomerServiceImpl implements ExternalCustomerService {
	 
	private static final String GET_USER_API = "https://avocado.od-tech.my/api/customer?idType={idType}&idNumber={idNumber}";
	private static final String GET_USER_ROLE_API = "https://avocado.od-tech.my/api/customerRole?customerId={customerId}";
	private static final String JSON_CUSTOMER_NODE = "customer";
	private static final String REQUEST_PARAM_IDTYPE = "idType";
	private static final String REQUEST_PARAM_IDNUMBER = "idNumber";
	private static final String REQUEST_PARAM_CUSTOMER_ID = "customerId";
	
	RestTemplate restTemplate = new RestTemplate();
		
	public LinkedHashMap<String, String> getCustomer(String idType, String idNumber) throws JsonMappingException, JsonProcessingException {
		Customer customer = new Customer();
		
		LinkedHashMap<String, String> customerMap = null;
		LinkedHashMap<String, String> roleMap = null;
		
		Map<String, String> customerParam = new HashMap<>();
		customerParam.put(REQUEST_PARAM_IDTYPE, idType);
		customerParam.put(REQUEST_PARAM_IDNUMBER, idNumber);
		
		//Retrieve customer details
		ResponseEntity<String> customerResult = restTemplate.getForEntity(GET_USER_API, String.class, customerParam);

		if (HttpStatus.OK.equals(customerResult.getStatusCode())) {
			ObjectMapper custObjMapper = new ObjectMapper();
			JsonNode custNode = custObjMapper.readTree(customerResult.getBody());
			String customerStr = custNode.get(JSON_CUSTOMER_NODE).toString();
			customer = custObjMapper.readValue(customerStr, Customer.class);
			
			customerMap = new ObjectMapper().readValue(customerResult.getBody(), LinkedHashMap.class);
			
			//Verify customer
			boolean validCustomer = verifyCustomerResult(customer.getId());
			
			if (validCustomer) {
				Map<String, Integer> customerRoleParam = new HashMap<>();
				customerRoleParam.put(REQUEST_PARAM_CUSTOMER_ID, customer.getId());
				
				//Retrieve customer role details
				ResponseEntity<String> customerRoleResult = restTemplate.getForEntity(GET_USER_ROLE_API, String.class, customerRoleParam);
				
				if (HttpStatus.OK.equals(customerRoleResult.getStatusCode())) {					
					roleMap = new ObjectMapper().readValue(customerRoleResult.getBody(), LinkedHashMap.class);
					
					//Combine both customer & customer role result
					customerMap.putAll(roleMap);
				}
			}
		}

		return customerMap;
	}
	
	public boolean verifyCustomerResult(long id) {
		return (id != 0) ? true : false; 
	}
}
