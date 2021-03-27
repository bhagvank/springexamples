package org.springexamples.jqueryrest.message;

public class Request {
	private Long customerId;
	private String order;

	public Request() {
	}

	public Request(Long customerId, String order) {
		this.customerId = customerId;
		this.order = order;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
