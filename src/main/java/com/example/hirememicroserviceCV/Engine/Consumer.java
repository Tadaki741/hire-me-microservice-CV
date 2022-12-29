package com.example.hirememicroserviceCV.Engine;

import com.example.hirememicroserviceCV.Model.CV;
import com.example.hirememicroserviceCV.Service.CVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private CVService cvService;

    @KafkaListener(topics = "cv", groupId = "group_id")
    public void consume(CV cv) throws IOException {
        logger.info(String.format("#### -> Consumed cv -> %s", cv));
        //save message to database
        CV newCV = this.cvService.save(cv);
        logger.info(String.format("#### -> New cv created -> %s", newCV.getId()));

    }
}

