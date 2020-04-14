FROM openjdk:8-jdk-alpine
COPY ./core/target/core-*.jar core.jar
CMD java -jar core.jar