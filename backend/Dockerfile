# Exemplo de Dockerfile para uma aplicação Spring Boot
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/dac-server-0.0.1-SNAPSHOT.jar app.jar   
ENTRYPOINT ["java","-jar","/app.jar"]