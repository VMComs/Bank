package com.bank.authorization.exception;

/**
 * Ошибка создания объекта User в POST, PUT методах UserRestController
 */

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }
}
