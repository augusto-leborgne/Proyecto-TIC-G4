FROM openjdk:17-jdk-alpine

# Instala Maven en la imagen de Docker
RUN apk add --no-cache maven

WORKDIR /app

# Copia el código fuente de la aplicación al contenedor
COPY . .

# Da permisos de ejecución al archivo mvnw
RUN chmod +x mvnw

# Compila y empaqueta la aplicación
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080
EXPOSE 8080

# Establece el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "target/app.jar"]
