package com.example.shampoome_api.controller;

import com.example.shampoome_api.helper.Mapper;
import com.example.shampoome_api.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.logging.Logger;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "http://wfm.ngdevs.net/")
public class RestController {

    private final String camundaEndpoint = "http://lva924-server3.ec.tuwien.ac.at:8081/engine-rest/";

    private Logger logger = Logger.getLogger(RestController.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();
    private Mapper mapper = new Mapper();
    private Connection connection;

    public RestController() {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://vm40519.cs.easyname.systems:3306/wfm",
                    "wfmDbAdmin", "i325&GbGjgtdegaS");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    @PostMapping( "preferences")
    public ProcessIdResponse Preferences(@RequestBody @Validated Preferences preferences) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CamundaRequestMessage camundaRequestMessage = mapper.MapPreferencesToCamundaRequestMessage(preferences);

        try {
            HttpEntity<String> camundaRequest = new HttpEntity<>(objectMapper.writeValueAsString(camundaRequestMessage), headers);
            String result = restTemplate.postForObject(camundaEndpoint + "message", camundaRequest, String.class);
            CamundaResponseStartEvent[] messageObjects = objectMapper.readValue(result, CamundaResponseStartEvent[].class);
            String processId = messageObjects[0].getId();
            logger.info(processId); //processId
            return new ProcessIdResponse(processId);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return new ProcessIdResponse();
        }
    }

    @PostMapping("order")
    public String placeOrder(@RequestBody OrderInput order) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ExtendedCamundaRequestMessage camundaRequestMessage = mapper.MapOrderToCamundaRequestMessage(order);

        try {
            String jsonObject = objectMapper.writeValueAsString(camundaRequestMessage);
            logger.info("send order message to camunda: " + jsonObject);
            HttpEntity<String> camundaRequest = new HttpEntity<>(jsonObject, headers);
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
            String jsonObject = objectMapper.writeValueAsString(camundaRequestMessage);
            HttpEntity<String> camundaRequest = new HttpEntity<>(jsonObject, headers);
            logger.info("send feedback message to camunda: " + jsonObject);
            String result = new RestTemplate().postForObject(camundaEndpoint + "message", camundaRequest, String.class);

            logger.info(result);
            return result;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    @GetMapping("shampoo-details")
    public Shampoo getShampooDetails(@RequestParam String processId) {
        CheckProcessId(processId);

        try {
            String result = new RestTemplate().getForObject(camundaEndpoint + "process-instance/" + processId + "/variables", String.class);
            logger.info(result);
            CamundaResponseShampoo messageObjects = objectMapper.readValue(result, CamundaResponseShampoo.class);
            Shampoo shampoo = new Shampoo();
            shampoo.setNickName(messageObjects.getNickName());
            shampoo.setIngredients(messageObjects.getIngredients());
            shampoo.setDescription(messageObjects.getDescription());
            shampoo.setBottleSize(messageObjects.getBottleSize());
            shampoo.setCost(messageObjects.getCost());
            if(shampoo.getIngredients() == null || shampoo.getDescription() == null ||
                    shampoo.getBottleSize() == null || shampoo.getCost() == null) {
                return new Shampoo();
            } else {
                return shampoo;
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    @GetMapping("order-id")
    public String GetOrderId(@RequestParam String processId) {
        PreparedStatement pstmt = null;
        String result = null;
        try {
            pstmt = connection.prepareStatement("select * from orders order where processId = ?");
            pstmt.setString(1, processId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                result = GetOrderFromResultSet(rs).orderId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return result;
    }

    @GetMapping("order")
    public OrderOutput GetOrder(String orderId) {
        PreparedStatement pstmt = null;
        OrderOutput result = null;
        try {
            pstmt = connection.prepareStatement("select * from orders order where orderId = ?");
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                result = GetOrderFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return result;
    }

    private OrderOutput GetOrderFromResultSet(ResultSet rs) throws SQLException {
        OrderOutput orderOutput = new OrderOutput();
        orderOutput.orderId = rs.getString("orderId");
        orderOutput.processId = rs.getString("ProcessId");
        orderOutput.NickName = rs.getString("Nickname");
        orderOutput.MatriculationNumber = rs.getString("MatriculationNumber");
        orderOutput.ShippingAddress = rs.getString("Address");
        orderOutput.ingredients = rs.getString("Ingredients");
        orderOutput.price = rs.getInt("Price");
        orderOutput.bottleSize = rs.getString("bottleSize");
        orderOutput.isDelayed = rs.getBoolean("isDelayed");
        orderOutput.status = rs.getString("Status");
        orderOutput.description = rs.getString("description");
        return orderOutput;
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
