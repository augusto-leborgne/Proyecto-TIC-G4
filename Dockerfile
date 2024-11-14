# Opción 1:
# FROM openjdk:21-jdk

# Opción 2:
FROM openjdk:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app
COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/tu-proyecto.jar"]
