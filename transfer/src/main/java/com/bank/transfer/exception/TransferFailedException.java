package com.bank.transfer.exception;

/**
 * Исключение, выбрасываемое при неудачной банковской транзакции
 */
public class TransferFailedException extends RuntimeException {
    public TransferFailedException(String message) {
        super(message);
    }
}
