package com.MyProf.MyProf_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class.
 */
@SpringBootApplication
public class MyProfBackendApplication {
    
    /**
     * Private constructor to hide the implicit public one.
     */
    private MyProfBackendApplication() {
    }

    /**
     * Main method to start the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(MyProfBackendApplication.class, args);
    }

}
