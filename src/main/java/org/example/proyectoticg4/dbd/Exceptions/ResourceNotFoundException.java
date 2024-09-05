// src/main/java/com/example/cinemaapp/exception/ResourceNotFoundException.java
package org.example.proyectoticg4.dbd.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
