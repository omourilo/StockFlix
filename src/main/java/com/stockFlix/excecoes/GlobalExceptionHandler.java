package com.stockFlix.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleIllegalArgument(InsufficientStockException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Global Error: " + ex.getMessage());
    }
    
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<String> handleIllegalArgument(LoginAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Global Error: " + ex.getMessage());
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleIllegalArgument(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Global Error: " + ex.getMessage());
    }
    
    @ExceptionHandler(PopulatedDeleteException.class)
    public ResponseEntity<String> handleIllegalArgument(PopulatedDeleteException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Global Error: " + ex.getMessage());
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleIllegalArgument(DisabledEntityException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Global Error: " + ex.getMessage());
    }
}
