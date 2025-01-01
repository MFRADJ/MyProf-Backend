package com.MyProf.MyProf_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for MyProf Backend.
 * This class serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
public final class MyProfBackendApplication {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class that should not be instantiated.
     */
    private MyProfBackendApplication() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(MyProfBackendApplication.class, args);
    }
}
