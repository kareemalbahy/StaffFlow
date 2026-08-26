package com.ems.StaffFlow.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Resource not found exception handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Email already exists exception handler
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //request payload validation errors handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            exception.getMessage(),
            webRequest.getDescription(false),
            errors
        );
        return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);
    }

    //all uncaught glopal exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception , WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(), 
            exception.getMessage()!=null? exception.getMessage() : "An unexpected error occurred", 
            webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
