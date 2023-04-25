package com.bank.history.handler;

import com.bank.history.exception.HistoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * класс для вывода ошибок рестконтроллера при ненахождении в БД
 * указывается класс исключения
 * возвращает статус ответа и сообщение исключения
 */
@RestControllerAdvice
public class HistoryExceptionHandler {
    @ExceptionHandler(HistoryNotFoundException.class)
    public ResponseEntity<String> handler(HistoryNotFoundException exception) {
        return new ResponseEntity<> (exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

