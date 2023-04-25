package com.bank.transfer.exceptions;

/**
 * Исключение, выбрасываемое при неудачной банковской транзакции
 */
public class TransferFailedException extends RuntimeException {
    public TransferFailedException(String message) {
        super(message);
    }
}
