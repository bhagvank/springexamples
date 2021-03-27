package org.springexamples.batch.decorator.aggregator;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springexamples.batch.decorator.model.Employee;

public class CustomLineAggregator implements LineAggregator<Employee> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String aggregate(Employee item) {
		try {
			return objectMapper.writeValueAsString(item);
		} catch (Exception e) {
			throw new RuntimeException("Unable to serialize Employee", e);
		}
	}
}
