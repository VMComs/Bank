package com.bank.publicinfo.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * NotFoundException - исключение, выбрасываемое при отсутствии искомого объекта
 *
 */

@Getter
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
