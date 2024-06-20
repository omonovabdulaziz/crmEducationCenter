FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/crm-0.0.1-SNAPSHOT.jar /app/crm-0.0.1-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/crm-0.0.1-SNAPSHOT.jar"]
