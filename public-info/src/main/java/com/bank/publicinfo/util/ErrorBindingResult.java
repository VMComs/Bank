package com.bank.publicinfo.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

/**
 * Класс ErrorBindingResult - используется в контроллерах для получения ошибок
 *
 */

public class ErrorBindingResult {

    public static String getBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("; <br />"));
    }
}
