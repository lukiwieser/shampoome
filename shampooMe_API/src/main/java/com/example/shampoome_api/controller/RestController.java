package com.example.shampoome_api.controller;

import com.example.shampoome_api.model.CamundaRequestMessage;
import com.example.shampoome_api.model.CamundaResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Logger;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final String camundaEndpoint = "http://lva924-server3.ec.tuwien.ac.at:8081/engine-rest/message";

    private Logger logger = Logger.getLogger(RestController.class.getName());

    @PostMapping( "sendPreferences")
    public void sendPreferences() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CamundaRequestMessage message = new CamundaRequestMessage();
        message.setMessageName("StartEvent");
        Dictionary<String, Object> processVariables = new Hashtable<>();
        Dictionary<String, String> preferences = new Hashtable<>();
        processVariables.put("preferences", preferences);
        preferences.put("value","dry hair");
        preferences.put("type", "String");
        message.setProcessVariables(processVariables);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HttpEntity<String> camundaRequest = new HttpEntity<>(objectMapper.writeValueAsString(message), headers);
            String result = restTemplate.postForObject(camundaEndpoint, camundaRequest, String.class);
            CamundaResponseMessage[] messageObjects = objectMapper.readValue(result, CamundaResponseMessage[].class);
            logger.info(messageObjects[0].getId()); //processId
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}
