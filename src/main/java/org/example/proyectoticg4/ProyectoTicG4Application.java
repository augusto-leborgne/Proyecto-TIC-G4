package org.example.proyectoticg4;

import org.example.proyectoticg4.dbd.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class ProyectoTicG4Application {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoTicG4Application.class, args);
	}

	// Get a database connection
	Connection connection = DatabaseConnection.getConnection();

	// Use the connection to execute SQL queries and interact with the database
	try {
		System.out.println("Conexión establecida.");
		String sql = "CREATE TABLE IF NOT EXISTS empleados ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "nombre VARCHAR(100) NOT NULL, "
				+ "apellido VARCHAR(100) NOT NULL, "
				+ "email VARCHAR(100) NOT NULL UNIQUE, "
				+ "fecha_contratacion DATE NOT NULL"
				+ ")";

		try (Statement statement = connection.createStatement()) {
			statement.execute(sql);
			System.out.println("Tabla creada exitosamente.");
		}

	} catch(SQLException e) {
		e.printStackTrace();

	} finally {
		// Close the connection when done
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Error closing connection: " + e.getMessage());
			}
		}
	}
}
