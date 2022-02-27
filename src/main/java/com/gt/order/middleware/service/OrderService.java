package com.gt.order.middleware.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gt.order.middleware.jto.OrderRequest;
import com.gt.order.middleware.model.Customer;
import com.gt.order.middleware.model.Transaction;

public interface OrderService {
	Transaction saveOrder(OrderRequest orderRequest) throws JsonProcessingException;
	Customer searchOrder(Customer customer);
	Transaction updateOrderStatus(Transaction trx);
}
