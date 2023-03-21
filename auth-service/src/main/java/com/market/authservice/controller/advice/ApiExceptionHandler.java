package com.market.authservice.controller.advice;

import com.market.authservice.exception.*;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<ApiError> handleFeignException(FeignException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, exception.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(
            value = {
                    IncorrectPasswordException.class,
                    IncorrectEmailException.class,
                    TokenExpiredException.class
            })
    public ResponseEntity<ApiError> handlePasswordEmailTokenException(RuntimeException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, exception.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleUserNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiError(NOT_FOUND, exception.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(value = {EmailAlreadyExist.class})
    public ResponseEntity<ApiError> handleEmailAlreadyExistException(EmailAlreadyExist exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, "email already exist", Collections.emptyList()));
    }
}
