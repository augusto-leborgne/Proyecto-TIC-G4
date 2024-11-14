# Usar una imagen de OpenJDK 21
FROM openjdk:21-jdk

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

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
CMD ["java", "-jar", "target/Proyecto-TIC-G4.jar"]
