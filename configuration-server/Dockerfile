FROM openjdk:11-jdk-slim

ENV APP_NAME="configuration-service"

VOLUME /tmp

EXPOSE 8888

ADD /build/libs/configuration-service-0.0.1-SNAPSHOT.jar configuration-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","configuration-service-0.0.1-SNAPSHOT.jar"]
