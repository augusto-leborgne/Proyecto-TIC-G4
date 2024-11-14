# Cambiar a una versión disponible de OpenJDK en Alpine
FROM openjdk:17-jdk-alpine

# Instalar Maven en Alpine
RUN apk add --no-cache maven

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar todos los archivos al contenedor
COPY . .

# Dar permisos de ejecución al script `mvnw`
RUN chmod +x mvnw

# Construir el proyecto
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/tu-proyecto.jar"]
