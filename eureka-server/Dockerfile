FROM openjdk:11-jdk-slim

ENV APP_NAME="eureka-server"

VOLUME /tmp

EXPOSE 8761

ADD /build/libs/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","eureka-server-0.0.1-SNAPSHOT.jar"]
