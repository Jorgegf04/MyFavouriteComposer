package com.example.favourite_composer.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global REST exception handler for the application.
 * Centralizes error handling using @RestControllerAdvice.
 */
@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ComposerNotFoundException.class)
    public ResponseEntity<?> handleComposerNotFound(
            ComposerNotFoundException ex, WebRequest request) {

        ApiErrorBody body = new ApiErrorBody(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MusicalPieceNotFoundException.class)
    public ResponseEntity<?> handleMusicalPieceNotFound(
            MusicalPieceNotFoundException ex, WebRequest request) {

        ApiErrorBody body = new ApiErrorBody(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyMusicalPiecesException.class)
    public ResponseEntity<?> handleEmptyMusicalPieces(
            EmptyMusicalPiecesException ex, WebRequest request) {

        ApiErrorBody body = new ApiErrorBody(
                LocalDateTime.now(),
                HttpStatus.NO_CONTENT,
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ApiErrorBody apiError = new ApiErrorBody(
                LocalDateTime.now(),
                status,
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return ResponseEntity.status(status).headers(headers).body(apiError);
    }
}

/**
 * Custom error body returned to REST clients.
 */
@AllArgsConstructor
@Getter
class ApiErrorBody {
    private LocalDateTime timestamp;
    private HttpStatusCode status;
    private String message;
    private String path;
}
