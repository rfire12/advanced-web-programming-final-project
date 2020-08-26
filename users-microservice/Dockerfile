FROM openjdk:11-jdk-slim

ENV APP_NAME="users-microservice"

VOLUME /tmp

EXPOSE 3698

ADD /build/libs/users-microservice-0.0.1-SNAPSHOT.jar users-microservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","users-microservice-0.0.1-SNAPSHOT.jar"]
