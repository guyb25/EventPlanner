# Use an official Maven image as the base image
FROM maven:3.8.5-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Maven project file and download dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn -B package

# Command to run the application
CMD ["java", "-jar", "target/EventPlanner.jar"]