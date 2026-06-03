package com.bridgelabz.quantitymeasurementApp.exception;

import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MeasurementResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        return ResponseEntity.badRequest().body(MeasurementResponseDTO.error(errorMessage));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MeasurementResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(MeasurementResponseDTO.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MeasurementResponseDTO> handleGlobalException(Exception ex) {
        return ResponseEntity.status(500).body(MeasurementResponseDTO.error("An unexpected error occurred: " + ex.getMessage()));
    }
}
