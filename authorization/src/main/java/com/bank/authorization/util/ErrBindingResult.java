package com.bank.authorization.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import java.util.stream.Collectors;

/**
 * Класс для преобразования поля ошибок BindingResult в отформатированную строку
 * Используется в RestController
 */

@Component
public class ErrBindingResult {

    public String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}
