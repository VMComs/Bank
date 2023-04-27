package com.bank.transfer.exception;

/**
 * Исключение, выбрасываемое, если транзакция не была найдена
 */
public class TransferNotFoundException extends RuntimeException {
    public TransferNotFoundException(String message) {
        super(message);
    }
}
