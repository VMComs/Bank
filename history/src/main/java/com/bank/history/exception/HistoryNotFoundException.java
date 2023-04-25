package com.bank.history.exception;

import lombok.AllArgsConstructor;

/**
 * класс своего исключения для обработки запроса с несуществующим id
 */
@AllArgsConstructor
public class HistoryNotFoundException extends RuntimeException {

    public HistoryNotFoundException (String message) {
        super(message);
    }
}
