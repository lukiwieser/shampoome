package com.example.shampoome_api.controller;

import com.example.shampoome_api.helper.Mapper;
import com.example.shampoome_api.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final String camundaEndpoint = "http://lva924-server3.ec.tuwien.ac.at:8081/engine-rest/";

    private Logger logger = Logger.getLogger(RestController.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();
    private Mapper mapper = new Mapper();

    @PostMapping( "preferences")
    public String Preferences(@RequestBody @Validated Preferences preferences) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CamundaRequestMessage camundaRequestMessage = mapper.MapPreferencesToCamundaRequestMessage(preferences);

        try {
            HttpEntity<String> camundaRequest = new HttpEntity<>(objectMapper.writeValueAsString(camundaRequestMessage), headers);
            String result = restTemplate.postForObject(camundaEndpoint + "message", camundaRequest, String.class);
            CamundaResponseMessage[] messageObjects = objectMapper.readValue(result, CamundaResponseMessage[].class);
            String processId = messageObjects[0].getId();
            logger.info(processId); //processId
            return processId;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    @GetMapping("shampoo-details")
    public String getShampooDetails(@RequestParam String processId) {
        CheckProcessId(processId);

        try {
            String result = new RestTemplate().getForObject(camundaEndpoint + "process-instance/" + processId + "/variables", String.class);
            logger.info(result);
            return result;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    @PostMapping("order")
    public String placeOrder(@RequestBody OrderInput order) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CamundaRequestMessage camundaRequestMessage = mapper.MapOrderToCamundaRequestMessage(order);

        try {
            HttpEntity<String> camundaRequest = new HttpEntity<>(objectMapper.writeValueAsString(camundaRequestMessage), headers);
            String result = restTemplate.postForObject(camundaEndpoint + "message", camundaRequest, String.class);
            logger.info(result);
            return result;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    @PostMapping("feedback")
    public String sendFeedback(@RequestBody Feedback feedback){
        CheckProcessId(feedback.processId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CamundaRequestMessage camundaRequestMessage = mapper.MapFeedbackToCamundaRequestMessage(feedback);

        try {
            HttpEntity<String> camundaRequest = new HttpEntity<>(objectMapper.writeValueAsString(camundaRequestMessage), headers);
            String result = new RestTemplate().postForObject(camundaEndpoint + "message", camundaRequest, String.class);
            logger.info(result);
            return result;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    private void CheckProcessId(String processId) {
        try {
            new RestTemplate().getForObject(camundaEndpoint + "process-instance/" + processId, String.class);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the process with the given id could not be found");
        }
    }
}
