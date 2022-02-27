package com.gt.order.middleware.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Transaction")
public class Transaction {
	@Id
	@GeneratedValue
	private int trxRefId;
	private String status;
	private String statusDesc;
	private String remarks;
	private LocalDateTime created;
	private LocalDateTime lastUpdated;
	
	@OneToMany(targetEntity=Orders.class, cascade=CascadeType.ALL)
	@JoinColumn(name="trx_id", referencedColumnName="trxRefId")
	private List<Orders> orders;
	
}
