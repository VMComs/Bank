package com.bank.authorization.handler;

import com.bank.authorization.exception.AuditNotCreatedException;
import com.bank.authorization.exception.AuditNotFoundException;
import com.bank.authorization.exception.RoleNotCreatedException;
import com.bank.authorization.exception.RoleNotFoundException;
import com.bank.authorization.exception.UserNotCreatedException;
import com.bank.authorization.exception.UserNotFoundException;
import com.bank.authorization.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Handler для обработки ошибок RestController
 * При возникновении ошибки создает объект класса ErrorResponse(message, currentTimeMillis)
 * Возвращает ResponseEntity(ErrorResponse, HttpStatus)
 * Аннотация ControllerAdvice - для создания бина ErrorHandler и указания, что это обработчик ошибок
 */

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(UserNotFoundException e) {
        log.error("User not found: {} {}", e, e.getMessage());
        final ErrorResponse userErrorResponse =
                new ErrorResponse("User with this id not found", Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(UserNotCreatedException e) {
        log.error("Error object in controller: {} {}", e, e.getMessage());
        final ErrorResponse userErrorResponse = new ErrorResponse(e.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(RoleNotFoundException e) {
        log.error("Role not found: {} {}", e, e.getMessage());
        final ErrorResponse roleErrorResponse =
                new ErrorResponse("Role with this id not found", Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(roleErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(RoleNotCreatedException e) {
        log.error("Error object in controller: {} {}", e, e.getMessage());
        final ErrorResponse roleErrorResponse = new ErrorResponse(e.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(roleErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(AuditNotFoundException e) {
        log.error("Audit not found: {} {}", e, e.getMessage());
        final ErrorResponse auditErrorResponse =
                new ErrorResponse("Audit with this id not found", Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(auditErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(AuditNotCreatedException e) {
        log.error("Error object in controller: {} {}", e, e.getMessage());
        final ErrorResponse auditErrorResponse =
                new ErrorResponse(e.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(auditErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
