package org.springexamples.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component 
public class H2DatabaseLoader implements CommandLineRunner { 

	private final PersonRepository repository;

	@Autowired 
	public H2DatabaseLoader(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception { 
		this.repository.save(new Person("John", "Smith", "Doctor"));
        this.repository.save(new Person("Brad", "Smith", "Engineer"));
        this.repository.save(new Person("Gregory", "Smith", "Mechanic"));
	}
}

