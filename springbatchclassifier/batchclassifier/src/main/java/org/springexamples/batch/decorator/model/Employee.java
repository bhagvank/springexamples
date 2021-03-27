package org.springexamples.batch.decorator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Employee {

	private Long id;
	private String firstName;
	private String lastName;
	private String birthdate;
}
