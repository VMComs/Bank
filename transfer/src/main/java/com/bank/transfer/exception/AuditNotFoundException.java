package com.bank.transfer.exception;

/**
 * Исключение, выбрасываемое если запись аудита не была найдена
 */
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
