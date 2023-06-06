import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.camunda.bpm.client.variable.ClientValues;
import org.camunda.bpm.client.variable.value.JsonValue;
import org.camunda.bpm.client.variable.value.XmlValue;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class Client {
    public static void main(String... args) throws JAXBException {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest/")
                .asyncResponseTimeout(10000)
                .disableBackoffStrategy()
                .disableAutoFetching()
                .maxTasks(1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Marshaller customerMarshaller = JAXBContext.newInstance(CustomerPreferences.class).createMarshaller();
        customerMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        TopicSubscriptionBuilder xmlSubscriptionBuilder = client.subscribe("findIngredients")
                .lockDuration(20000)
                .handler((externalTask, externalTaskService) -> {
                    CustomerPreferences preferences = createCustomerFromVariables(externalTask);
                    try {
                        //TODO: do some random shampoo selection
                        Shampoo shampoo = new Shampoo();

                        StringWriter stringWriter = new StringWriter();
                        customerMarshaller.marshal(shampoo, stringWriter);
                        String shampooXml = stringWriter.toString();
                        VariableMap variables = Variables.createVariables().putValue("shampoo", ClientValues.xmlValue(shampooXml));
                        externalTaskService.complete(externalTask, variables);
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                });

        TopicSubscriptionBuilder jsonSubscriptionBuilder = client.subscribe("jsonCustomerCreation")
                .lockDuration(20000)
                .handler((externalTask, externlTaskService) -> {
                    CustomerPreferences customer = createCustomerFromVariables(externalTask);
                    try {
                        String customerJson = objectMapper.writeValueAsString(customer);
                        VariableMap variables = Variables.createVariables().putValue("customer", ClientValues.jsonValue(customerJson));
                        externlTaskService.complete(externalTask, variables);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                });

        TopicSubscriptionBuilder readSubscrptionBuilder = client.subscribe("customerReading")
                .lockDuration(20000)
                .handler((externalTask, externalTaskService) -> {
                    String dataformat = externalTask.getVariable("dataFormat");
                    if ("json".equals(dataformat)) {
                        JsonValue jsonCustomer = externalTask.getVariableTyped("customer");
                        System.out.println("Customer json: " + jsonCustomer.getValue());
                    } else if ("xml".equals(dataformat)) {
                        XmlValue xmlCustomer = externalTask.getVariableTyped("customer");
                        System.out.println("Customer xml: " + xmlCustomer.getValue());
                    }
                    externalTaskService.complete(externalTask);
                });

        client.start();
        xmlSubscriptionBuilder.open();
        jsonSubscriptionBuilder.open();
        readSubscrptionBuilder.open();
    }

    private static CustomerPreferences createCustomerFromVariables(ExternalTask externalTask) {
        CustomerPreferences customer = new CustomerPreferences();

        //TODO: set values

        return customer;
    }
}