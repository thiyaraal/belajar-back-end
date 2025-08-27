package com.example.demo.config;

import com.example.demo.api.ErrorResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, String path) {
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /* ===================== EXPLICIT (spesifik) ===================== */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex,
                                                             HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, "Data constraint violation", req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest req) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .path(req.getRequestURI())
                .details(details)
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    /* ===================== GENERIC (fallback) ===================== */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOthers(Exception ex, HttpServletRequest req) {
        // 0) Unwrap ke root cause jika ter-embed di NestedServletException, dll.
        Throwable root = ex;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }

        // 1) ResponseStatusException → hormati status & reason
        if (ex instanceof ResponseStatusException rse) {
            return build(HttpStatus.valueOf(rse.getStatusCode().value()), rse.getReason(), req.getRequestURI());
        }
        if (root instanceof ResponseStatusException rrse) {
            return build(HttpStatus.valueOf(rrse.getStatusCode().value()), rrse.getReason(), req.getRequestURI());
        }

        // 2) Exception dengan @ResponseStatus → hormati anotasinya
        var annEx = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (annEx != null) {
            return build(annEx.code(), ex.getMessage(), req.getRequestURI());
        }
        var annRoot = AnnotationUtils.findAnnotation(root.getClass(), ResponseStatus.class);
        if (annRoot != null) {
            return build(annRoot.code(), root.getMessage(), req.getRequestURI());
        }

        // 3) (Opsional) tangani NotFound/BadRequest kalau muncul di root (tanpa @ResponseStatus)
        if (root instanceof NotFoundException nfe) {
            return build(HttpStatus.NOT_FOUND, nfe.getMessage(), req.getRequestURI());
        }
        if (root instanceof BadRequestException bre) {
            return build(HttpStatus.BAD_REQUEST, bre.getMessage(), req.getRequestURI());
        }

        // 4) Fallback 500
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI());
    }
}