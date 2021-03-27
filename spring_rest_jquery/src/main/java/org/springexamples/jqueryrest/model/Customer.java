package org.springexamples.jqueryrest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class Customer extends ResourceSupport {

	private Long customerId;
	private String name;

	private List<Order> orders;

	public Customer() {
	}

	public Customer(Long customerId, String name) {
		this.customerId = customerId;
		this.name = name;
		orders = new ArrayList<>();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
