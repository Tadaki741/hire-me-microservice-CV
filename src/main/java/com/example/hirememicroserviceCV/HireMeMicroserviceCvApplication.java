package com.example.hirememicroserviceCV;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

@SpringBootApplication
public class HireMeMicroserviceCvApplication {

    private static final Logger logger = Logger.getLogger(HireMeMicroserviceCvApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(HireMeMicroserviceCvApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            try {
                //Initialize the firebase SDK
                ClassLoader classLoader = HireMeMicroserviceCvApplication.class.getClassLoader();
                InputStream serviceAccount = classLoader.getResourceAsStream("serviceAccountKey.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                logger.info(" --> Firebase SDK Initialized");
            } catch (FileNotFoundException e) {
                logger.severe(" --> JSON resource file not found !!!");
            }
        };
    }

}
