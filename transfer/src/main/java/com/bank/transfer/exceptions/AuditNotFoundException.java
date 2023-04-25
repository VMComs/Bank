package com.bank.transfer.exceptions;

/**
 * Исключение, выбрасываемое если запись аудита не была найдена
 */
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
