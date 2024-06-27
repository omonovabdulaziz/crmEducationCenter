# Use a base image that includes Java and whatever else you need
FROM openjdk:17-alpine

# Install Redis client tools
RUN apk update && \
    apk add redis

# Set the working directory
WORKDIR /app

# Copy your application JAR or WAR file
COPY target/crm-0.0.1-SNAPSHOT.jar /app/crm-0.0.1-SNAPSHOT.jar

EXPOSE 8084

# Specify the command to run your application
CMD ["java", "-jar", "/app/crm-0.0.1-SNAPSHOT.jar"]
