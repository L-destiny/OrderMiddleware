package com.gt.order.middleware.service.external;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.gt.order.middleware.model.CustomerRole;
import com.gt.order.middleware.service.ExternalCustomerService;

@Service
public class ExternalCustomerServiceImpl implements ExternalCustomerService {
	 
	private static final String GET_USER_API = "https://avocado.od-tech.my/api/customer?idType={idType}&idNumber={idNumber}";
	private static final String GET_USER_ROLE_API = "https://avocado.od-tech.my/api/customerRole?customerId={customerId}";
	private static final String JSON_CUSTOMER_NODE = "customer";
	private static final String JSON_CUSTOMER_ROLE_NODE = "roles";
	
	private static final String REQUEST_PARAM_IDTYPE = "idType";
	private static final String REQUEST_PARAM_IDNUMBER = "idNumber";
	private static final String REQUEST_PARAM_CUSTOMER_ID = "customerId";
	
	RestTemplate restTemplate = new RestTemplate();
		
	public List<ResponseEntity<String>> getCustomer(String idType, String idNumber) throws JsonMappingException, JsonProcessingException {
		Customer customer = new Customer();
		CustomerRole customerRole = new CustomerRole();
		
		Map<String,Object> map1;
		
		Map<String, String> customerParam = new HashMap<>();
		customerParam.put(REQUEST_PARAM_IDTYPE, idType);
		customerParam.put(REQUEST_PARAM_IDNUMBER, idNumber);
		
		ResponseEntity<String> customerResult = restTemplate.getForEntity(GET_USER_API, String.class, customerParam);
		ResponseEntity<String> customerRoleResult = new ResponseEntity<String>(HttpStatus.OK);

		if (HttpStatus.OK.equals(customerResult.getStatusCode())) {
			ObjectMapper custObjMapper = new ObjectMapper();
			JsonNode custNode = custObjMapper.readTree(customerResult.getBody());
			String customerStr = custNode.get(JSON_CUSTOMER_NODE).toString();
			customer = custObjMapper.readValue(customerStr, Customer.class);
			
			map1 = new ObjectMapper().readValue(customerStr, HashMap.class);
			
			boolean validCustomer = verifyCustomerResult(customer.getId());
			
			if (validCustomer) {
				Map<String, Integer> customerRoleParam = new HashMap<>();
				customerRoleParam.put(REQUEST_PARAM_CUSTOMER_ID, customer.getId());
				
				customerRoleResult = restTemplate.getForEntity(GET_USER_ROLE_API, String.class, customerRoleParam);
				
				if (HttpStatus.OK.equals(customerRoleResult.getStatusCode())) {
					ObjectMapper custRoleObjMapper = new ObjectMapper();
					JsonNode custRoleNode = custRoleObjMapper.readTree(customerRoleResult.getBody());
					String customerRoleStr = custRoleNode.get(JSON_CUSTOMER_ROLE_NODE).toString();
					System.out.println("list ="+customerRoleStr);
					List<CustomerRole> customerRoles = Arrays.asList(custRoleObjMapper.readValue(customerRoleStr, CustomerRole[].class));
					
					//List<Map<String, Object>> map2 = new ObjectMapper().readValue(customerRoleStr, List.class);

				}
			}
		}
		
		List<ResponseEntity<String>> responseList = Arrays.asList(customerResult, customerRoleResult);
		System.out.println(responseList.toString());
		//TODO: Combine 2 results

		return responseList;
	}
	
	public boolean verifyCustomerResult(long id) {
		return (id != 0) ? true : false; 
	}
}
