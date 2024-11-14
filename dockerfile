FROM openjdk:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "target/app.jar"]
