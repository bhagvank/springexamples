package org.springexamples.react;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity 
public class Person {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String comments;

	private Person() {}

	public Person(String firstName, String lastName, String comments) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.comments = comments;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Person person = (Person) object;
		return Objects.equals(id, person.id) &&
			Objects.equals(firstName, person.firstName) &&
			Objects.equals(lastName, person.lastName) &&
			Objects.equals(comments, person.comments);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, firstName, lastName, comments);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Person{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", comments='" + comments + '\'' +
			'}';
	}
}

