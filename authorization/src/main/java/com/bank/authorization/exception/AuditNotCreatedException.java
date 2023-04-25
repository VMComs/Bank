package com.bank.authorization.exception;

/**
 * Ошибка создания объекта Audit в POST, PUT методах AuditRestController
 */

public class AuditNotCreatedException extends RuntimeException {
    public AuditNotCreatedException(String message) {
        super(message);
    }
}
