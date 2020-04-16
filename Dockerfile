FROM openjdk:8-jdk-alpine
COPY ./core/target/core-*.jar core.jar
COPY ./application.properties application.properties
CMD java -jar core.jar