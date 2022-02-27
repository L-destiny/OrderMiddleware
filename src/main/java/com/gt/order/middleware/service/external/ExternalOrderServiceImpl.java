package com.gt.order.middleware.service.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.order.middleware.model.Orders;
import com.gt.order.middleware.service.ExternalOrderService;

@Service
public class ExternalOrderServiceImpl implements ExternalOrderService {
	
	private static final String SUBMIT_ORDER_API = "https://avocado.od-tech.my/api/purchase";
	
	private static final String REQUEST_PARAM_CUSTOMERID = "customerId";
	private static final String REQUEST_PARAM_ORDER = "order";
	
	RestTemplate restTemplate = new RestTemplate();

	@Override
	public ResponseEntity<String> submitOrders(int customerId, List<Orders> orders) throws JsonProcessingException {
		
		ObjectMapper orderObjMapper = new ObjectMapper();
		String orderJson = orderObjMapper.writeValueAsString(orders);
		
		Map<String, String> orderParam = new HashMap<>();
		orderParam.put(REQUEST_PARAM_CUSTOMERID, String.valueOf(customerId));
		System.out.println("orderParam="+orderParam.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add(REQUEST_PARAM_CUSTOMERID, String.valueOf(customerId));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<String> orderResult = restTemplate.postForEntity( SUBMIT_ORDER_API, request , String.class );

		//Error page return from client backend API
		//Object orderResult = restTemplate.postForObject(SUBMIT_ORDER_API, orderParam, String.class);
		//ResponseEntity<String> orderResult = restTemplate.postForEntity(SUBMIT_ORDER_API, String.class, null, orderParam);
		
		System.out.println("orderResult="+orderResult.toString());

		return null;
	}
	
}
