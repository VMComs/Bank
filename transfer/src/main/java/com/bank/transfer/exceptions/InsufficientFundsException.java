package com.bank.transfer.exceptions;

/**
 * Исключение, выбрасываемое при недостаточном балансе на счете отправителя
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
