package org.springexamples.batch.decorator.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springexamples.batch.decorator.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Employee.builder().id(rs.getLong("id"))
				.firstName(rs.getString("firstName"))
				.lastName(rs.getString("lastName"))
				.birthdate(rs.getString("birthdate")).build();
	}
}
