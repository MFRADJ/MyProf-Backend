package com.MyProf.MyProf_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Main Spring Boot Application class.
 */
@SuppressWarnings("checkstyle:FinalClass")
@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.MyProf.MyProf_Backend.user", "com.MyProf.MyProf_Backend.roles"})
public class MyProfBackendApplication {
    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(MyProfBackendApplication.class, args);
    }
}
