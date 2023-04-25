package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Класс ErrorHandler - обработчик ошибок RestController.
 * При возникновении ошибки создает объект класса ErrorResponse(message, currentTimeMillis)
 * Возвращает ResponseEntity(ErrorResponse, HttpStatus)
 * Аннотация ControllerAdvice - для создания бина ErrorHandler и указания, что это обработчик ошибок
 *
 * @see NotFoundException
 */

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public static ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {
        log.error("Объект не найден: " + e + " " + e.getMessage());
        ErrorResponse response = new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public static ResponseEntity<ErrorResponse> notExecutedException(NotExecutedException e) {
        log.error("В контроллере получен некорректный объект: " + e + " " + e.getMessage());
        ErrorResponse response = new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}


