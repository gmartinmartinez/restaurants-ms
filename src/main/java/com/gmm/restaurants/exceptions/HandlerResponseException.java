package com.gmm.restaurants.exceptions;


import static java.util.Objects.isNull;

import com.gmm.restaurants.model.api.ErrorResponse;
import com.gmm.restaurants.model.api.ErrorResponse.SeverityEnum;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class HandlerResponseException {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> toResponseMethodArgumentNotValidException(
        MethodArgumentNotValidException methodArgumentNotValidException) {
        String message =
            methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.error(message);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse.builder()
                    .code("NOT_VALID_ARGUMENT")
                    .severity(SeverityEnum.ERROR)
                    .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> toResponseHttpMessageNotReadableException(
        HttpMessageNotReadableException httpMessageNotReadableException) {
        String message =
            String.format("MessageNotReadable error: %s",
                isNull(httpMessageNotReadableException.getRootCause())
                    ? httpMessageNotReadableException.getMessage() :
                    httpMessageNotReadableException.getRootCause().getMessage());
        log.error(message);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse.builder()
                    .code("MESSAGE_NOT_READABLE")
                    .severity(SeverityEnum.ERROR)
                    .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> toResponseMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException exception) {
        String message =
            String.format("MethodArgumentTypeMismatchException error: %s",
                isNull(exception.getRootCause())
                    ? exception.getMessage() :
                    exception.getRootCause().getMessage());
        log.error(message);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse.builder()
                    .code("TYPE_MISMATCH")
                    .severity(SeverityEnum.ERROR)
                    .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> toResponseNotFoundException(
        NotFoundException notFoundException) {
        log.error(notFoundException.toString());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse.builder()
                    .code("NOT_FOUND_ITEM")
                    .severity(SeverityEnum.ERROR)
                    .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> toResponseAnyException(Exception exception) {
        log.error("Unhandled exception", exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse.builder()
                    .code("SERVER_ERROR")
                    .severity(SeverityEnum.CRITICAL)
                    .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .build());
    }

}
