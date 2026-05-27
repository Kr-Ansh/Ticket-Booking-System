package com.irons.ticketbookingsystem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException exception) {
        log.error("Global Watchtower intercepted an operational exception: {}", exception.getMessage());

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.CONFLICT.value());
        errorBody.put("error", "Conflict / Operational Failure");
        errorBody.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidateExceptions(MethodArgumentNotValidException exception) {
        log.warn("Global Watchtower intercepted a bad client request payload.");

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Bad Request / Validation Failure");

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        errorBody.put("validationErrors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
}
