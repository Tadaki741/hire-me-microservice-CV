package com.example.hirememicroserviceCV.Service;


import com.example.hirememicroserviceCV.HttpResponse.ResponseBody;
import com.example.hirememicroserviceCV.Model.LoginBody;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class RestService {

    private static final Logger logger = Logger.getLogger(RestService.class.getName());
    private final RestTemplate restTemplate;


    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    //Calling microservice-User
    public boolean verifyIDToken(String requestHeaderToken) {

        String url = "http://localhost:18080/users/verify";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put("idToken", requestHeaderToken);
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<ResponseBody> response = this.restTemplate.postForEntity(url, entity, ResponseBody.class);
        logger.info(" --> CHECKING RESPONSE STATUS: " + response.getBody());

        //Checking value
        return Objects.requireNonNull(response.getBody()).getData().equals(true);
    }


}
