package com.gt.order.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gt.order.middleware.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
