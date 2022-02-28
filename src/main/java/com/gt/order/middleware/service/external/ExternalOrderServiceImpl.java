package com.gt.order.middleware.service.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.order.middleware.model.Orders;
import com.gt.order.middleware.service.ExternalOrderService;

@Service
public class ExternalOrderServiceImpl implements ExternalOrderService {
	
	private static final String SUBMIT_ORDER_API = "https://avocado.od-tech.my/api/order";
	
	private static final String REQUEST_PARAM_CUSTOMERID = "customerId";
	private static final String REQUEST_PARAM_ORDER = "order";
	
	RestTemplate restTemplate = new RestTemplate();

	@Override
	public ResponseEntity<String> submitOrders(int customerId, List<Orders> orders) throws JsonProcessingException {
		//Convert order list to json string
		ObjectMapper orderObjMapper = new ObjectMapper();
		String orderJson = orderObjMapper.writeValueAsString(orders);
		
		//Assign parameters
		Map<String, String> orderParam = new HashMap<>();
		orderParam.put(REQUEST_PARAM_CUSTOMERID, String.valueOf(customerId));
		orderParam.put(REQUEST_PARAM_ORDER, orderJson);
		
		//POST to client API
		ResponseEntity<String> orderResult = restTemplate.postForEntity(SUBMIT_ORDER_API, orderParam , String.class);

		return orderResult;
	}
	
}
