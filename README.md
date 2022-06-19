# shampoome

The project consists of multiple projects, one of them is the frontend project while all the other ones take different roles of backend projects. All of the backend projects are written in Java and are Spring Boot applications. The spring projects can be started using the command 'mvn spring-boot:run'.

## engine
This project is the camunda engine. That is where the bpmn process should be deployed using the tenant-id 'shampoome-process'. When deploying the bpmn file itself and the quality-check.form file should be attached to the deployment. That is the core of the whole application.

## shampooMe_API
This project is the backend for the frontend project and starts a new process after a user enters it's preferences.

## frontend
It is a single page application written in angular and serves as the only interface for the end user. The frontend communicates only with the shampooMe_API.

## recommender-system
This is the project containing the external tasks of the recommender-system resource which are responsible for finding the suitable ingredients based on the user preferences as well as a task that persists the order of the user to the database.

## delivery
This is the project containing the external tasks of the delivery resource - update the current delivery status to on_the_way, as well as the delivery task itself as well as the task that updates the status in case of a delay.

## customer-relations
This project contains the tasks that set the orderStatus to delivered as well as the persisting of the feedback.