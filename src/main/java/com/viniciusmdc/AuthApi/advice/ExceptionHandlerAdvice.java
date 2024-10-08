package com.viniciusmdc.AuthApi.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandlerAdvice
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {ResponseStatusException.class, ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(
            ResponseStatusException ex, WebRequest request) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", ex.getReason());
        bodyOfResponse.put("status", ex.getStatusCode().value());
        bodyOfResponse.put("error", ex.getStatusCode());
        bodyOfResponse.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(bodyOfResponse, ex.getStatusCode());
    }

    @ExceptionHandler(value
            = {Exception.class, Exception.class})
    protected ResponseEntity<Object> handleDefaultException(
            Exception ex, WebRequest request) {
        Map<String, Object> bodyOfResponse = new HashMap<>();
        bodyOfResponse.put("message", ex.getMessage());
        bodyOfResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        bodyOfResponse.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
        bodyOfResponse.put("timestamp", LocalDateTime.now());

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
