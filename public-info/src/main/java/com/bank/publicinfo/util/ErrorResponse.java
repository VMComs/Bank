package com.bank.publicinfo.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

/**
 * Класс ErrorResponse - используется в ErrorHandler для возвращения объекта ошибки
 *
 */

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;

    private HttpStatus status;

    private String error;
}
