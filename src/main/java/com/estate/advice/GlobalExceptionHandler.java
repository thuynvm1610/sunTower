package com.estate.advice;

import com.estate.exception.BusinessException;
import com.estate.exception.InputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(
                Map.of("message", ex.getMessage())
        );
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<?> handleValidationException(InputValidationException ex) {
        return ResponseEntity.badRequest().body(
                Map.of("message", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("message", "Đã xảy ra lỗi hệ thống!")
        );
    }
}
