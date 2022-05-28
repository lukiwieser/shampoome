import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;

import org.camunda.bpm.client.ExternalTaskClient;

public class Client {
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        LOGGER.info("starting client...");

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("find-ingredients")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    LOGGER.info("i am working on a task...");

                    // Get a process variable
                    String preferences = externalTask.getVariable("preferences");
                    LOGGER.info("preferences: " + preferences);
                    // Integer amount = externalTask.getVariable("amount");

                    // LOGGER.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

                    /*
                    try {
                        Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}