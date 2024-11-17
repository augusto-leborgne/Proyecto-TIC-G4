package org.example.proyectoticg4;

import org.example.proyectoticg4.exceptions.GlobalExceptionHandler;
import org.example.proyectoticg4.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound_shouldReturnNotFoundResponse() {
        ResponseEntity<String> response = exceptionHandler.handleResourceNotFound(new ResourceNotFoundException("Test Error"));

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Test Error", response.getBody());
    }

    @Test
    void handleIllegalArgument_shouldReturnBadRequestResponse() {
        ResponseEntity<String> response = exceptionHandler.handleIllegalArgument(new IllegalArgumentException("Invalid argument"));

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid argument", response.getBody());
    }

    @Test
    void handleValidationExceptions_shouldReturnErrorMap() {
        Map<String, String> errors = Map.of("field", "error");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(
                new MockValidationException(errors));

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(errors, response.getBody());
    }
}

// Mock class for testing validation exceptions
class MockValidationException extends org.springframework.web.bind.MethodArgumentNotValidException {
    private final Map<String, String> errors;

    public MockValidationException(Map<String, String> errors) {
        super(null, null);
        this.errors = errors;
    }

}
