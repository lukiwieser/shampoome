package customer.relations.application;

import org.camunda.bpm.client.ExternalTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class CRClientApplication implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(CRClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CRClientApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        LOGGER.info("starting client...");

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://lva924-server3.ec.tuwien.ac.at:8081/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();
        // subscribe to an external task topic as specified in the process
        client.subscribe("set-delivered")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    LOGGER.info("i am setting delivery status...");
                    String processId = externalTask.getProcessInstanceId();

                    Connection connection;

                    try {
                        connection = DriverManager.getConnection("jdbc:mariadb://vm40519.cs.easyname.systems:3306/wfm",
                                "wfmDbAdmin", "i325&GbGjgtdegaS");
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = connection.prepareStatement("UPDATE Orders SET Status = ?" +
                                    " WHERE processId = ?");
                            pstmt.setString(1, "delivered");
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

                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
        // subscribe to an external task topic as specified in the process
        client.subscribe("save-feedback-kpis")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    LOGGER.info("i am saving feedback and KPIs...");

                    String matrNumber = externalTask.getVariable("matriculationNumber");
                    String name = externalTask.getVariable("nickName");
                    int overallSatisfaction = externalTask.getVariable("overallSatisfaction");
                    int priceSatisfaction = externalTask.getVariable("priceSatisfaction");
                    String comment = externalTask.getVariable("comment");
                    Date processStartTime = externalTask.getVariable("ProcessStartTime");
                    Timestamp sqlProcessStartTime = new Timestamp(processStartTime.getTime());
                    LOGGER.info("processStartTime: " + processStartTime);
                    Date processEndTime = externalTask.getVariable("ProcessEndTime");
                    Timestamp sqlProcessEndTime = new Timestamp(processEndTime.getTime());
                    LOGGER.info("processEndTime: " + processEndTime);
                    Date qualityCheckStartTime = externalTask.getVariable("QualityCheckStartTime");
                    Timestamp sqlQualityCheckStartTime = new Timestamp(qualityCheckStartTime.getTime());
                    LOGGER.info("qualityCheckStartTime: " + qualityCheckStartTime);
                    Date qualityCheckEndTime = externalTask.getVariable("QualityCheckEndTime");
                    Timestamp sqlQualityCheckEndTime = new Timestamp(qualityCheckEndTime.getTime());
                    LOGGER.info("qualityCheckEndTime: " + qualityCheckEndTime);

                    Connection connection;

                    try {
                        connection = DriverManager.getConnection("jdbc:mariadb://vm40519.cs.easyname.systems:3306/wfm",
                                "wfmDbAdmin", "i325&GbGjgtdegaS");
                        PreparedStatement pstmt = null;
                        try {

                            pstmt = connection.prepareStatement("INSERT INTO Feedback (MatriculationNumber," +
                                    " Name, OverallSatisfaction, PriceSatisfaction, Comment, ProcessStartTime, ProcessEndTime," +
                                    " QualityCheckStartTime, QualityCheckEndTime)" +
                                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            pstmt.setString(1, matrNumber);
                            pstmt.setString(2, name);
                            pstmt.setInt(3, overallSatisfaction);
                            pstmt.setInt(4, priceSatisfaction);
                            pstmt.setString(5, comment);
                            pstmt.setTimestamp(6, sqlProcessStartTime);
                            pstmt.setTimestamp(7, sqlProcessEndTime);
                            pstmt.setTimestamp(8, sqlQualityCheckStartTime);
                            pstmt.setTimestamp(9, sqlQualityCheckEndTime);
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
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
