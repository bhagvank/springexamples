package org.springexamples.client;

import static org.assertj.core.api.BDDAssertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = com.example.serviceregistrationanddiscoveryclient.ServiceRegistrationAndDiscoveryClientApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ServiceRegistrationAndDiscoveryClientAppTests {

	static ConfigurableApplicationContext eurekaServer;

	@BeforeAll
	public static void startEureka() {
		eurekaServer = SpringApplication.run(EurekaServer.class,
				"--server.port=8761",
				"--eureka.instance.leaseRenewalIntervalInSeconds=1");
	}

	@AfterAll
	public static void closeEureka() {
		eurekaServer.close();
	}

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldRegisterClientInEurekaServer() throws InterruptedException {
		Thread.sleep(3000);

		ResponseEntity<String> response = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/service-instances/rest-service-client", String.class);

		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		then(response.getBody()).contains("rest-service-client");
	}

	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaServer
	static class EurekaServer {
	}
}
