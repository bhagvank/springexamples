package org.springexamples.jqueryrest.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import org.springexamples.jqueryrest.model.Customer;

@Repository
public class CustomerRepository {

	private final List<Customer> customers = new ArrayList<>();

	public CustomerRepository() {
		this.customers.add(new Customer(1L, "Thomas Smith"));
		this.customers.add(new Customer(2L, "Jack Smith"));
		this.customers.add(new Customer(3L, "George Kay"));
        this.customers.add(new Customer(4L, "Joanne Smith"));
        this.customers.add(new Customer(5L, "Seanne Kane"));
	}

	public List<Customer> findAll() {
		return this.customers;
	}

	public Customer findOne(Long id) {

		for (Customer customer : this.customers) {
			if (customer.getCustomerId().equals(id)) {
				return customer;
			}
		}
		return null;
	}
}
