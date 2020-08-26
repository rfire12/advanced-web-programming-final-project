FROM openjdk:11-jdk-slim

ENV APP_NAME="events-microservice"

VOLUME /tmp

EXPOSE 7452

ADD /build/libs/events-microservice-0.0.1-SNAPSHOT.jar events-microservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","events-microservice-0.0.1-SNAPSHOT.jar"]
