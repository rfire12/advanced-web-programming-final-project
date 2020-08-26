FROM openjdk:11-jdk-slim

ENV APP_NAME="zuul-server"

VOLUME /tmp

EXPOSE 8080

ADD /build/libs/zuul-server-0.0.1-SNAPSHOT.jar zuul-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","zuul-server-0.0.1-SNAPSHOT.jar"]
