FROM openjdk:8-jdk-alpine
COPY core/target/uploader-*.jar uploader.jar
CMD java -jar uploader.jar