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
	
}
