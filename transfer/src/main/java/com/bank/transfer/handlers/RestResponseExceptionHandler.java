package com.bank.transfer.handlers;

import com.bank.transfer.exceptions.InsufficientFundsException;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.TransferNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, отвечающий за корректное представление ошибок в виде Http-ответа
 */
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Метод, обрабатывающий ошибки валидации
     * @param ex ArgumentNotValidException
     * @param headers http headers
     * @param status http статус
     * @param request http запрос
     * @return http ответ, в котором содержится информация о невалидном поле и сообщение ошибки
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
    /**
     * Метод, обрабатывающий исключения, если что-либо не было найдено
     * @param ex NotFoundException
     * @param request http-запрос
     * @return текст исключения и 404 Http-ошибка
     */
    @ExceptionHandler(value = {AccountNotFoundException.class, TransferNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    /**
     * Метод, обрабатывающий исключения, если транзакция не была выполнена
     * @param ex InsufficientFundsException
     * @param request http-запрос
     * @return текст исключения и Http-ошибка
     */
    @ExceptionHandler(value = {InsufficientFundsException.class})
    protected ResponseEntity<Object> handleTransferFailedException(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
