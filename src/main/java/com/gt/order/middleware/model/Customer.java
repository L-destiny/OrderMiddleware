package com.gt.order.middleware.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Customer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
	@Id
	//@GeneratedValue
	private int id;
	
	@Column(name="id_type")
	private String idType;
	
	@Column(name="id_number")
	private String idNumber;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(targetEntity=Orders.class, cascade=CascadeType.ALL)
	@JoinColumn(name="cust_id", referencedColumnName="id")
	private List<Orders> orders;

}
