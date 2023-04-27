package com.bank.transfer.handler;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.ServiceNotAvailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Класс, отвечающий за обработку ошибок, возникающих в feign клиентах
 */
public class FeignClientErrorDecoder implements ErrorDecoder {

    /**
     * Метод-декодер ошибок
     * @param response ответ feign клиента
     * @return
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        final int badRequestError = 400;
        final int notFoundError = 404;
        final int internalServerError = 500;

        switch (response.status()) {
            case badRequestError -> {
                return new RuntimeException("Bad request");
            } case notFoundError -> {
                return new AccountNotFoundException("Account not found");
            } case internalServerError -> {
                return new ServiceNotAvailableException("Service is unavailable");
            } default -> {
                return new Exception("Exception while getting data");
            }
        }
    }
}
