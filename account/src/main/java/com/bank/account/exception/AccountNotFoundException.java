package com.bank.account.exception;

/**
 * Нестандартный класс-исключение для обработки запроса с несуществующим id
*/

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
