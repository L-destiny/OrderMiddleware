package com.gt.order.middleware.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gt.order.middleware.constant.OrderMiddlewareConstant;
import com.gt.order.middleware.exception.ResourceNotFoundException;
import com.gt.order.middleware.jto.OrderRequest;
import com.gt.order.middleware.model.Customer;
import com.gt.order.middleware.model.Transaction;
import com.gt.order.middleware.repository.CustomerRepository;
import com.gt.order.middleware.repository.TransactionRepository;
import com.gt.order.middleware.service.OrderService;
import com.gt.order.middleware.service.external.ExternalOrderServiceImpl;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private TransactionRepository transactionRepo;
	private ExternalOrderServiceImpl exOrderServiceImpl;
	
	public OrderServiceImpl(CustomerRepository customerRepo, ExternalOrderServiceImpl exOrderServiceImpl) {
		super();
		this.customerRepo = customerRepo;
		this.exOrderServiceImpl = exOrderServiceImpl;
	}
	
	@Override
	public Transaction saveOrder(OrderRequest orderRequest) throws JsonProcessingException {
		
		//Save customer & order to database
		Customer customer =  customerRepo.save(orderRequest.getCustomer());
		
		//Save transaction to database
		Transaction trxRecord = new Transaction();
		trxRecord.setTrxRefId(orderRequest.getCustomer().getId());
		//Status & status description should be in pair
		trxRecord.setStatus(OrderMiddlewareConstant.TRX_STATUS_SUBMITTED);
		trxRecord.setStatusDesc(OrderMiddlewareConstant.TRX_STATUS_DESC_SUBMITTED);
		trxRecord.setCreated(LocalDateTime.now());
		trxRecord.setLastUpdated(LocalDateTime.now());
		trxRecord.setOrders(customer.getOrders());
		
		Transaction trx = transactionRepo.save(trxRecord);

		//Call external service submitOrder
		ResponseEntity<String> orders = exOrderServiceImpl.submitOrders(orderRequest.getCustomer().getId(), customer.getOrders());
		
		//Update order status to In Progress for success client submitOrder API call
		if (HttpStatus.OK.equals(orders.getStatusCode())) {
			trx.setStatus(OrderMiddlewareConstant.TRX_STATUS_INPROGRESS);
			trx.setStatusDesc(OrderMiddlewareConstant.TRX_STATUS_DESC_INPROGRESS);
			updateOrderStatus(trx);
		}

		return trx;
	}
	
	@Override
	public Customer searchOrder(Customer customer) {
		return (Customer) customerRepo.findById(customer.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Order", "Customer ID", customer.getId()));
	}
	
	@Override
	public Transaction updateOrderStatus(Transaction trx) {
		int trxId = trx.getTrxRefId();
		Transaction existingTrx = transactionRepo.findById(trxId).orElseThrow(
				() -> new ResourceNotFoundException("Transaction", "Id", trxId));
		
		//When update status, should update status description together
		existingTrx.setStatus(trx.getStatus());
		existingTrx.setRemarks(trx.getRemarks());
		return transactionRepo.save(existingTrx);
	}
}
