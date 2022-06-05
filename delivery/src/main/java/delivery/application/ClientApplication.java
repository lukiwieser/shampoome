package delivery.application;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        LOGGER.info("starting client...");

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://lva924-server3.ec.tuwien.ac.at:8081/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();
        // subscribe to an external task topic as specified in the process
        client.subscribe("set-on-the-way")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    LOGGER.info("i am setting the delivery status...");
                    String processId = externalTask.getProcessInstanceId();

                    Connection connection;

                    try {
                        connection = DriverManager.getConnection("jdbc:mariadb://vm40519.cs.easyname.systems:3306/wfm",
                                "wfmDbAdmin", "i325&GbGjgtdegaS");
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = connection.prepareStatement("UPDATE Orders SET Status = ?" +
                                    " WHERE processId = ?");
                            pstmt.setString(1, "on_the_way");
                            pstmt.setString(2, processId);
                            pstmt.executeQuery();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            try {
                                pstmt.close();
                            } catch (SQLException e) {
                                LOGGER.warn(e.getMessage());
                            }
                        }
                    } catch (SQLException e) {
                        LOGGER.warn(e.getMessage());
                    }

                    externalTaskService.complete(externalTask);
                })
                .open();
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
        // subscribe to an external task topic as specified in the process
        client.subscribe("set-is-delayed")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    LOGGER.info("i am setting the delivery status...");
                    String processId = externalTask.getProcessInstanceId();

                    Connection connection;

                    try {
                        connection = DriverManager.getConnection("jdbc:mariadb://vm40519.cs.easyname.systems:3306/wfm",
                                "wfmDbAdmin", "i325&GbGjgtdegaS");
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = connection.prepareStatement("UPDATE Orders SET isDelayed = ?" +
                                    " WHERE processId = ?");
                            pstmt.setBoolean(1, true);
                            pstmt.setString(2, processId);
                            pstmt.executeQuery();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            try {
                                pstmt.close();
                            } catch (SQLException e) {
                                LOGGER.warn(e.getMessage());
                            }
                        }
                    } catch (SQLException e) {
                        LOGGER.warn(e.getMessage());
                    }

                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
