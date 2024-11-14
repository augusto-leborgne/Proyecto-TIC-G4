# Usar una imagen de Maven con Java 21
FROM maven:3.8.6-eclipse-temurin-21

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar todos los archivos al contenedor
COPY . .

# Construir el proyecto
RUN mvn clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/Proyecto-TIC-G4.jar"]
