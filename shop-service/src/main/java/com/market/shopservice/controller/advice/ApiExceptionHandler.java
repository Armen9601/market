package com.market.shopservice.controller.advice;

import com.market.shopservice.exception.*;
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

    @ExceptionHandler(value = {HaveNotEnoughMoneyException.class})
    public ResponseEntity<ApiError> handleHaveNotEnoughMoneyException(HaveNotEnoughMoneyException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, "You have not enough money", Collections.emptyList()));
    }

    @ExceptionHandler(value = {ProductHasNotInStorageException.class})
    public ResponseEntity<ApiError> handleProductHasNotInStorageException(ProductHasNotInStorageException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, "We have not this product in storage", Collections.emptyList()));
    }

    @ExceptionHandler(value = {CommentWritingBlockedException.class})
    public ResponseEntity<ApiError> handleCommentWritingBlockedException(CommentWritingBlockedException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, "You can write comment after purchasing the product", Collections.emptyList()));
    }

    @ExceptionHandler(value = {SetRatingBlockedException.class})
    public ResponseEntity<ApiError> SetRatingBlockedException(SetRatingBlockedException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiError(BAD_REQUEST, "You can set rating after purchasing the product", Collections.emptyList()));
    }
}
