package com.example.hirememicroserviceCV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class HireMeMicroserviceCvApplication {

    private static final Logger logger = Logger.getLogger(HireMeMicroserviceCvApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(HireMeMicroserviceCvApplication.class, args);
    }

}
