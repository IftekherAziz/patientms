# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Set the working directory in the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Make sure the Maven Wrapper script is executable
RUN chmod +x ./mvnw

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Copy the built jar file from the target directory to the working directory
COPY target/patientms-0.0.1-SNAPSHOT.jar Patience.jar

# Run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "Patience.jar"]
