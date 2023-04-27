package com.bank.transfer.exception;

/**
 * Исключение, выбрасываемое при недостаточном балансе на счете отправителя
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
