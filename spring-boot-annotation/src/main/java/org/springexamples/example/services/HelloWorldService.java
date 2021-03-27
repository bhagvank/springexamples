package org.springexamples.example.services;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {
    public String sayMessage() {
        return "Hello world!!!!";
    }
}

