package com.bank.publicinfo.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * NotExecutedException - исключение, выбрасываемое при ошибке сохранения/обновления объекта
 *
 */

@Getter
@NoArgsConstructor
public class NotExecutedException extends RuntimeException {

    public NotExecutedException(String message) {
        super(message);
    }
}
