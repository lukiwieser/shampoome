package controller;

import model.Customer;
import model.Preferences;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Value("camunda.engine.endpoint")
    private String camundaEndpoint;

    private Logger logger = Logger.getLogger(RestController.class.getName());

    @PostMapping("/sendPreferences")
    public void sendPreferences(@RequestBody Preferences preferences, @RequestBody Customer customer) {
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.postForEntity(camundaEndpoint + "/start-event", preferences, Object.class);
        logger.info(result.toString());
    }
}
