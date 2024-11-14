# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Configure the working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/Proyecto-TIC-G4-0.0.1-SNAPSHOT.jar"]
