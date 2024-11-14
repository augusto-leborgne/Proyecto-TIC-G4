# Use a compatible Maven image with Java 21
FROM maven:3.9.3-eclipse-temurin-21

# Configure the working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/Proyecto-TIC-G4.jar"]
