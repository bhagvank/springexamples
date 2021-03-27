package org.springexamples.example.controllers;

import org.springexamples.example.services.HelloWorldService;
import org.springexamples.example.services.MySQLDatabaseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private final HelloWorldService service;
    MySQLDatabaseService mySQLService;

    public HelloWorldController(HelloWorldService service, MySQLDatabaseService mySQLService) {
        this.service = service;
        this.mySQLService = mySQLService;
    }

    @GetMapping(value="/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHelloWorld() {
        return service.sayMessage();
    }
}
