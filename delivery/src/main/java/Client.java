import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class Client {
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        LOGGER.info("starting client...");

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("generate-delivery-params")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    VariableMap variables = Variables.createVariables();
                    LOGGER.info("i am working on a task...");

                    // generate a random number between 0 and 1
                    // this variable is used for determining if a failure during the delivery process occurs
                    double rndDeliveryFailure = Math.random();
                    variables.put("rndDeliveryFailure", rndDeliveryFailure);

                    // Complete the task
                    externalTaskService.complete(externalTask, variables);
                })
                .open();
    }
}