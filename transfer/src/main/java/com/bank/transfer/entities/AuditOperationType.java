package com.bank.transfer.entities;

/**
 * Типы банковских транзакций для аудита:
 * SUCCESS_TRANSFER - банковская транзакция была выполнена успешно
 * FAILED_TRANSFER - ошибка во время транзакции, перевод не была выполнен
 */
public enum AuditOperationType {
    SUCCESS_TRANSFER, FAILED_TRANSFER
}
