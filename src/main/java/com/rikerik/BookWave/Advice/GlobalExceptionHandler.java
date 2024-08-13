package com.rikerik.BookWave.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rikerik.BookWave.Exception.HttpRequestException;
import com.rikerik.BookWave.Exception.JsonConversionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonConversionException.class)
    public ResponseEntity<String> handleJsonConversionException(JsonConversionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<String> handleHttpRequestException(HttpRequestException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

}
