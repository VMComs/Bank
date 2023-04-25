package com.bank.transfer.exceptions;


/**
 * Исключение, выбрасываемое при неудачной попытке связи с другим микросервисом
 */
public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException(String message) {
        super();
    }
}
