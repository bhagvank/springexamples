package org.springexamples.batch.decorator.classifier;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import org.springexamples.batch.decorator.model.Employee;

public class EmployeeClassifier implements Classifier<Employee, ItemWriter<? super Employee>> {

	private static final long serialVersionUID = 1L;
	
	private ItemWriter<Employee> evenItemWriter;
	private ItemWriter<Employee> oddItemWriter;

	public EmployeeClassifier(ItemWriter<Employee> evenItemWriter, ItemWriter<Employee> oddItemWriter) {
		this.evenItemWriter = evenItemWriter;
		this.oddItemWriter = oddItemWriter;
	}

	@Override
	public ItemWriter<? super Employee> classify(Employee employee) {
		return employee.getId() % 2 == 0 ? evenItemWriter : oddItemWriter;
	}
}
