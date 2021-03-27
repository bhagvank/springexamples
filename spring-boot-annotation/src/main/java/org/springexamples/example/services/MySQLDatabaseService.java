package org.springexamples.example.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.stereotype.Service;

@ConditionalOnResource(
        resources = "classpath:mysql.properties")
@Service
public class MySQLDatabaseService {
    //This class is available only if mysql.properties is present in the classpath
}
