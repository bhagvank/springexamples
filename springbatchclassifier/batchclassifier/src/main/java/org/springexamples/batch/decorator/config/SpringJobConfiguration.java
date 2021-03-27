package org.springexamples.batch.decorator.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import org.springexamples.batch.decorator.aggregator.CustomLineAggregator;
import org.springexamples.batch.decorator.classifier.EmployeeClassifier;
import org.springexamples.batch.decorator.mapper.EmployeeRowMapper;
import org.springexamples.batch.decorator.model.Employee;

@Configuration
public class SpringJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Employee> customerPagingItemReader() {

	

		JdbcPagingItemReader<Employee> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(1000);
		reader.setRowMapper(new EmployeeRowMapper());

        Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);

		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from employee");
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);
		return reader;
	}

	@Bean
	public FlatFileItemWriter<Employee> jsonItemWriter() throws Exception {

		String customerOutputPath = File.createTempFile("employeeOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = " + customerOutputPath);
		FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new CustomLineAggregator());
		writer.setResource(new FileSystemResource(customerOutputPath));
		writer.afterPropertiesSet();
		return writer;
	}

	@Bean
	public StaxEventItemWriter<Employee> xmlItemWriter() throws Exception {

		String customerOutputPath = File.createTempFile("employeeOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = " + customerOutputPath);
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("employee", Employee.class);
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);

		StaxEventItemWriter<Employee> writer = new StaxEventItemWriter<>();
		writer.setRootTagName("employees");
		writer.setMarshaller(marshaller);
		writer.setResource(new FileSystemResource(customerOutputPath));
		writer.afterPropertiesSet();
		return writer;
	}

	@Bean
	public ClassifierCompositeItemWriter<Employee> classifierEmployeeCompositeItemWriter() throws Exception {
		ClassifierCompositeItemWriter<Employee> compositeItemWriter = new ClassifierCompositeItemWriter<>();
		compositeItemWriter.setClassifier(new EmployeeClassifier(xmlItemWriter(), jsonItemWriter()));
		return compositeItemWriter;
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Employee, Employee>chunk(10)
				.reader(customerPagingItemReader())
				.writer(classifierEmployeeCompositeItemWriter())
				.stream(xmlItemWriter())
				.stream(jsonItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}

}