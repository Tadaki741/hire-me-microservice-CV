package com.example.hirememicroserviceCV.Engine;

import com.example.hirememicroserviceCV.Model.CV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "cv";

    @Autowired
    private KafkaTemplate<String, CV> kafkaTemplate;

    public void sendMessage(CV cv) {
        logger.info(String.format("#### -> adding new cv -> %s", cv));
        this.kafkaTemplate.send(TOPIC, cv);
    }
}
