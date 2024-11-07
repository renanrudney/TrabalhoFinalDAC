FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/ms-auth-*.jar app.jar   
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar","/app.jar"]