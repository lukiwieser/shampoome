FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre

RUN apk --no-cache add curl

ARG JAR_FILE=camunda.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
COPY --from=maven /usr/src/app/target/classes /opt/app/classes

HEALTHCHECK --interval=5s --timeout=10s CMD curl -f http://0.0.0.0:8081

ENTRYPOINT ["java","-jar","camunda.jar"]