package com.mykytaaa.user.profile.userprofileservice.rest.controller;

import com.mykytaaa.user.profile.userprofileservice.exception.ResourceNotFoundException;
import com.mykytaaa.user.profile.userprofileservice.rest.dto.ApiErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Collections;
import java.util.List;

/**
 * Global exception handler for REST controllers.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Error message for resource not found exception.
     */
    private static final String RESOURCE_NOT_FOUND = "Resource not found";

    /**
     * Error message for validation errors.
     */
    private static final String VALIDATION_ERROR = "Validation error occurred";


    /**
     * Handles ResourceNotFoundException and returns a ResponseEntity with appropriate status code and error message.
     *
     * @param e ResourceNotFoundException instance
     * @return ResponseEntity with error details
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleNotFoundException(ResourceNotFoundException e) {
        final ApiErrorDto response = new ApiErrorDto(
                HttpStatus.NOT_FOUND.value(), RESOURCE_NOT_FOUND, Collections.singletonList(e.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    /**
     * Handles WebExchangeBindException and returns a ResponseEntity with appropriate status code and error message.
     *
     * @param e WebExchangeBindException instance
     * @return ResponseEntity with error details
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiErrorDto> handleWebExchangeBindException(WebExchangeBindException e) {
        final List<String> details = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        final ApiErrorDto response = new ApiErrorDto(
                HttpStatus.BAD_REQUEST.value(), VALIDATION_ERROR, details
        );
        return ResponseEntity.badRequest().body(response);
    }
}
